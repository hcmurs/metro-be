package org.alfred.ticketservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.alfred.ticketservice.config.JwtUtil;
import org.alfred.ticketservice.exception.TicketProcessingException;
import org.alfred.ticketservice.dto.ticket.TicketQrData;
import org.alfred.ticketservice.dto.ticket.TicketRequest;
import org.alfred.ticketservice.dto.ticket.TicketResponse;
import org.alfred.ticketservice.dto.ticket.TicketScanRequest;
import org.alfred.ticketservice.model.FareMatrix;
import org.alfred.ticketservice.model.TicketTypes;
import org.alfred.ticketservice.model.TicketUsageLogs;
import org.alfred.ticketservice.model.Tickets;
import org.alfred.ticketservice.model.enums.Duration;
import org.alfred.ticketservice.model.enums.TicketStatus;
import org.alfred.ticketservice.model.enums.UsageTypes;
import org.alfred.ticketservice.repository.FareMatrixRepository;
import org.alfred.ticketservice.repository.TicketRepository;
import org.alfred.ticketservice.repository.TicketTypeRepository;
import org.alfred.ticketservice.repository.TicketUsageLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TicketServiceImpl implements TicketService,TicketCronJobService{
    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private TicketUsageLogRepository ticketUsageLogRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private FareMatrixRepository fareMatrixRepository;

    @Autowired
    private FareMatrixService fareMatrixService;

    @Autowired
    private QRService qrService;

    @Autowired
    private JwtUtil jwtUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.ticket.secret-key}")
    private String secretKey;

    @Override
    public TicketResponse getTicketById(Long id) {
        Tickets ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));
        return mapToResponse(ticket);
    }

    @Override
    public TicketResponse createTicketType(TicketRequest.TicketType ticket) {
        try {
            if (ticket == null || ticket.id() == null  ) {
                throw new IllegalArgumentException("Ticket request must contain valid fareMatrixId");
            }
            TicketTypes ticketType = ticketTypeRepository.findById(ticket.id()).orElseThrow(() -> new EntityNotFoundException("Ticket type not found with id: " + ticket.id()));
            if( !ticketType.isActive()) {
                throw new EntityNotFoundException("Ticket type is not active with id: " + ticket.id());
            }
            if( ticketType.getValidityDuration() <0 ) {
                throw new IllegalArgumentException("Ticket type must have a valid validity duration at least 0");
            }
            String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
            if(ticketType.isForStudent() && !jwtUtil.isStudent(token)){
                throw new IllegalArgumentException("This ticket only available for students");
            }
            String prefix = "MT";
            String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            String ticketCode = prefix + datePart + randomPart;
            Tickets newTicket = Tickets.builder()
                    .ticketType(ticketType)
                    .ticketCode(ticketCode)
                    .status(TicketStatus.PENDING)
                    .inTrip(false)
                    .build();
            newTicket.setValidFrom(LocalDateTime.now());
            newTicket.setValidUntil(LocalDateTime.now().plusDays(30));
            newTicket.setActualPrice(ticketType.getPrice());
            newTicket.setName(ticketType.getName());
            Tickets savedTicket = ticketRepository.save(newTicket);
            return mapToResponse2(savedTicket);
        } catch (FeignException.Forbidden ex){
            throw new IllegalArgumentException("This ticket only available for students");
        }
    }

    @Override
    @Transactional
    public TicketResponse createTicketFare(TicketRequest.FareMatrix ticket) {
        if (ticket == null || ticket.id() == null  ) {
            throw new IllegalArgumentException("Ticket request must contain valid fareMatrixId");
        }
        TicketTypes ticketType = ticketTypeRepository.findByValidityDuration(0);
        if (ticketType == null) {
            throw new EntityNotFoundException("Default ticket type not found");
        }
        FareMatrix fareMatrix = fareMatrixRepository.findById(ticket.id()).orElseThrow(() -> new EntityNotFoundException("Fare matrix not found with id: " + ticket.id()));
        if( fareMatrix.getStartStationId() == null || fareMatrix.getEndStationId() == null) {
            throw new EntityNotFoundException("Fare matrix must have valid start and end station IDs");
        }
        if(!fareMatrix.isActive()){
            throw new EntityNotFoundException("Fare matrix is not active with id: " + ticket.id());
        }
        String prefix = "MT";
        String datePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String ticketCode = prefix + datePart + randomPart;
        Tickets newTicket = Tickets.builder()
                .ticketType(ticketType)
                .fareMatrix(fareMatrix)
                .ticketCode(ticketCode)
                .status(TicketStatus.PENDING)
                .inTrip(false)
                .build();
        newTicket.setValidFrom(LocalDateTime.now());
        newTicket.setValidUntil(LocalDateTime.now().plusDays(30));
        newTicket.setActualPrice(fareMatrix.getFarePricing().getPrice());
        newTicket.setName(fareMatrix.getName());
        Tickets savedTicket = ticketRepository.save(newTicket);
        return mapToResponse(savedTicket);
    }

    @Override
    public TicketResponse updateTicket(TicketStatus status, Long id) {
        if (id == null || status == null) {
            throw new IllegalArgumentException("Ticket ID and status cannot be null");
        }
        Tickets ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + id));
        ticket.setStatus(status);
        Tickets updatedTicket = ticketRepository.save(ticket);
        return mapToResponse(updatedTicket);
    }

    @Override
    public TicketResponse getTicketByCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Ticket code cannot be empty");
        }
        Tickets ticket = ticketRepository.findByTicketCode(code);
        if (ticket == null) {
            throw new EntityNotFoundException("Ticket not found with code: " + code);
        }
        return mapToResponse(ticket);
    }

    @Override
    public TicketResponse getTicketByQr(String code) {
        return null;
    }

    @Override
    public List<TicketResponse> getTicketByFareMatrixId(Long fareMatrixId) {
        FareMatrix fareMatrix = fareMatrixRepository.findById(fareMatrixId)
                .orElseThrow(() -> new EntityNotFoundException("Fare matrix not found with id: " + fareMatrixId));
        List<Tickets> tickets = ticketRepository.findByFareMatrix(fareMatrix);
        return tickets.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public TicketResponse scanEntryTicket(TicketScanRequest ticketScanRequest) {
        if (ticketScanRequest.qrCodeJsonData().isEmpty() ||  ticketScanRequest.stationId() == null) {
            throw new IllegalArgumentException("QR code data and station ID are required");
        }
        try {
            LocalDateTime currentScanTime = LocalDateTime.now();
            TicketQrData ticketQrData = objectMapper.readValue(ticketScanRequest.qrCodeJsonData(), TicketQrData.class);
            if (!verifySignature(ticketQrData)) {
                throw new TicketProcessingException("Invalid QR code signature");
            }
            Tickets ticket = ticketRepository.findByTicketCode(ticketQrData.ticketCode());
            if (ticket == null) {
                throw new EntityNotFoundException("Ticket not found with code: " + ticketQrData.ticketCode());
            }

            if(ticket.getTicketType() == null) {
                throw new EntityNotFoundException("Ticket type not found for ticket with code: " + ticketQrData.ticketCode());
            }
            if (ticket.getValidUntil().isBefore(currentScanTime)) {
                ticket.setStatus(TicketStatus.EXPIRED);
                ticketRepository.save(ticket);
                throw new TicketProcessingException("Ticket has expired");
            }
            if(ticket.isInTrip()) {
                throw new TicketProcessingException("Must exit before re-entry");
            }
            if (ticket.getValidFrom().isAfter(currentScanTime)) {
                throw new TicketProcessingException("Ticket is not valid yet");
            }
            if(ticket.getTicketType().getValidityDuration() == 0) {
                    if(ticket.getFareMatrix() == null) {
                        throw new EntityNotFoundException("Fare matrix not found for ticket with code: " + ticketQrData.ticketCode());
                    }
                    if(ticket.getFareMatrix().getStartStationId() == null) {
                        throw new EntityNotFoundException("Start station not found for fare matrix of ticket with code: " + ticketQrData.ticketCode());
                    }
                    if(!ticket.getFareMatrix().isActive()){
                        throw new EntityNotFoundException("Fare matrix is not active for ticket with code: " + ticketQrData.ticketCode());
                    }
                    if(ticket.getStatus()!= TicketStatus.NOT_USED) {
                        throw new TicketProcessingException("Ticket is not in a valid state for entry");
                    }
                    if(!ticketScanRequest.stationId().equals(ticket.getFareMatrix().getStartStationId())) {
                        throw new TicketProcessingException("Ticket is not valid for entry this station");
                    }
                    ticket.setInTrip(true);
                    ticket.setStatus(TicketStatus.USED);
                    saveTicketUsageLog(ticket, UsageTypes.ENTRY, currentScanTime, ticketScanRequest.stationId());
                }
                else  {
                    if (ticket.getStatus() == TicketStatus.NOT_USED) {
                        ticket.setValidUntil(currentScanTime.plusDays(ticket.getTicketType().getValidityDuration()));
                        ticket.setStatus(TicketStatus.USED);
                        ticket.setInTrip(true);
                        saveTicketUsageLog(ticket, UsageTypes.ENTRY, currentScanTime, ticketScanRequest.stationId());
                    } else if (ticket.getStatus() == TicketStatus.USED) {
                        ticket.setInTrip(true);
                        saveTicketUsageLog(ticket, UsageTypes.ENTRY, currentScanTime, ticketScanRequest.stationId());
                    } else {
                        throw new TicketProcessingException("Ticket is not in a valid state for entry");
                    }
                }
            Tickets updatedTicket = ticketRepository.save(ticket);
            return mapToResponse(updatedTicket);
        } catch (JsonProcessingException e) {
            throw new TicketProcessingException("Error processing QR code data", e);
        }

    }

    @Override
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public TicketResponse scanExitTicket(TicketScanRequest ticketScanRequest) {
        if (ticketScanRequest.qrCodeJsonData().isEmpty() ||  ticketScanRequest.stationId() == null) {
            throw new IllegalArgumentException("QR code data and station ID are required");
        }
        try {
            LocalDateTime currentScanTime = LocalDateTime.now();
            TicketQrData ticketQrData = objectMapper.readValue(ticketScanRequest.qrCodeJsonData(), TicketQrData.class);
            if (!verifySignature(ticketQrData)) {
                throw new TicketProcessingException("Invalid QR code signature");
            }
            Tickets ticket = ticketRepository.findByTicketCode(ticketQrData.ticketCode());
            if (ticket == null) {
                throw new EntityNotFoundException("Ticket not found with code: " + ticketQrData.ticketCode());
            }

            if (ticket.getTicketType() == null) {
                throw new EntityNotFoundException("Ticket type not found for ticket with code: " + ticketQrData.ticketCode());
            }
            if (!ticket.isInTrip()) {
                throw new TicketProcessingException("Must enter before exit");
            }
            ticket.setInTrip(false);
            if (ticket.getStatus() == TicketStatus.USED) {
                    // Single-use ticket: mark as expired after exit
                    if (ticket.getFareMatrix() == null || ticket.getFareMatrix().getEndStationId() == null) {
                        throw new EntityNotFoundException("Fare matrix or end station not found for ticket with code: " + ticketQrData.ticketCode());
                    }

                    if (!fareMatrixService.isStationInFareMatrix(ticketScanRequest.stationId(), ticket.getFareMatrix().getFareMatrixId())) {
                        throw new TicketProcessingException("Ticket is not valid for exit at this station");
                    }
                    ticket.setStatus(TicketStatus.EXPIRED);
                }
               else {
                    // Multi-day pass: keep status as USED for future entries
                    if (ticket.getStatus() != TicketStatus.USED) {
                        throw new TicketProcessingException("Ticket is not in a valid state for exit");
                    }
                    // Status remains USED - no change needed
                }
            saveTicketUsageLog(ticket, UsageTypes.EXIT, currentScanTime, ticketScanRequest.stationId());
            Tickets updatedTicket = ticketRepository.save(ticket);
            return mapToResponse(updatedTicket);
        } catch (JsonProcessingException e) {
            throw new TicketProcessingException("Error processing QR code data", e);
        }
    }

    @Override
    @Transactional
    public TicketResponse cancel(Long ticketId) {
        Tickets ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + ticketId));
        if (ticket.getStatus() == TicketStatus.USED) {
            throw new TicketProcessingException("Cannot cancel a used ticket");
        }
        ticket.setStatus(TicketStatus.CANCELLED);
        return mapToResponse(ticketRepository.save(ticket));
    }

    @Override
    @Transactional
    public List<TicketResponse> getTicketsByTicketType(Long ticketTypeId) {
        log.info("Fetching tickets for ticket type ID: {}", ticketTypeId);
        if (ticketTypeId == null) {
            throw new IllegalArgumentException("Ticket type ID cannot be null");
        }
        TicketTypes ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket type not found with id: " + ticketTypeId));
        List<Tickets> tickets = ticketRepository.findByTicketType(ticketType);
        return tickets.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public byte[] generateQrCodeData(String ticketCode) {
    if (ticketCode == null || ticketCode.isBlank()) {
        throw new IllegalArgumentException("Ticket code cannot be empty");
    }
        Tickets ticket = ticketRepository.findByTicketCode(ticketCode);
        if (ticket == null) {
            throw new EntityNotFoundException("Ticket not found with code: " + ticketCode);
        }
        String qrContent = generateQrContent(ticket);
        try {
            return qrService.generateQrCode(qrContent);
        } catch (Exception e) {
            log.error("Error generating QR code for ticket code: {}", ticketCode, e);
            throw new TicketProcessingException("Failed to generate QR code", e);
        }
    }

    @Override
    public List<TicketResponse> getTicketsByIds(List<Long> ticketIds) {
    if (ticketIds == null || ticketIds.isEmpty()) {
        throw new IllegalArgumentException("Ticket IDs cannot be null or empty");
    }
        List<Tickets> tickets = ticketRepository.findByTicketIdIn(ticketIds);
        if (tickets.isEmpty()) {
            throw new EntityNotFoundException("No tickets found for the provided IDs");
        }
        return tickets.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TicketResponse> getTicketsByIdsAndStatus(List<Long> ticketIds, TicketStatus status) {
        if (ticketIds == null || ticketIds.isEmpty()) {
            throw new IllegalArgumentException("Ticket IDs cannot be null or empty");
        }
        if (status == null) {
            throw new IllegalArgumentException("Ticket status cannot be null");
        }
        List<Tickets> tickets = ticketRepository.findByTicketIdInAndStatus(ticketIds,status);
        if (tickets.isEmpty()) {
            throw new EntityNotFoundException("No tickets found for the provided IDs and status");
        }
        return tickets.stream()
                .filter(ticket -> ticket.getValidUntil().isAfter(LocalDateTime.now()))
                .sorted(Comparator.comparing(Tickets::getCreatedAt))
                .map(this::mapToResponse)
                .toList();
    }


    private TicketResponse mapToResponse(Tickets ticket) {
        return TicketResponse.builder()
                .id(ticket.getTicketId())
                .ticketTypeId(ticket.getTicketType().getTicketTypeId())
                .fareMatrixId(ticket.getFareMatrix() == null ? null : ticket.getFareMatrix().getFareMatrixId())
                .ticketCode(ticket.getTicketCode())
                .name(ticket.getName())
                .actualPrice(ticket.getActualPrice())
                .validFrom(ticket.getValidFrom())
                .validUntil(ticket.getValidUntil())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }

    private TicketResponse mapToResponse2(Tickets ticket) {
        return TicketResponse.builder()
                .id(ticket.getTicketId())
                .ticketTypeId(ticket.getTicketType().getTicketTypeId())
                .ticketCode(ticket.getTicketCode())
                .name(ticket.getName())
                .actualPrice(ticket.getActualPrice())
                .validFrom(ticket.getValidFrom())
                .validUntil(ticket.getValidUntil())
                .status(ticket.getStatus())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }

   private String generateSignature(TicketQrData ticketQrData) {
       try {
           StringBuilder data = new StringBuilder();
           data.append(ticketQrData.ticketTypeName() != null ? ticketQrData.ticketTypeName() : "")
               .append(ticketQrData.ticketId() != null ? ticketQrData.ticketId() : "")//.append(ticketQrData.name() != null ? ticketQrData.name() : "")
                   .append(ticketQrData.validFrom() != null ? ticketQrData.validFrom() : "")
                   .append(ticketQrData.validUntil() != null ? ticketQrData.validUntil() : "")
               .append(ticketQrData.ticketCode() != null ? ticketQrData.ticketCode() : "")
           ;
            log.info("Generating signature for QR data: {}", data);
           Mac sha256Hmac = Mac.getInstance("HmacSHA256");
           SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
           sha256Hmac.init(secretKeySpec);
           byte[] hash = sha256Hmac.doFinal(data.toString().getBytes(StandardCharsets.UTF_8));
           return Base64.getEncoder().encodeToString(hash);
       } catch (NoSuchAlgorithmException | InvalidKeyException e) {
           throw new TicketProcessingException("Failed to generate signature", e);
       }
   }

    private void saveTicketUsageLog(Tickets ticket, UsageTypes usageType, LocalDateTime usageTime, Long stationId) {
        TicketUsageLogs ticketUsageLog = TicketUsageLogs.builder()
                .ticket(ticket)
                .usageType(usageType)
                .usageTime(usageTime)
                .stationId(stationId)
                .build();
        ticketUsageLogRepository.save(ticketUsageLog);
    }

    private String generateQrContent(Tickets ticket) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String nowStr = LocalDateTime.now().format(formatter);
            String untilStr = LocalDateTime.now().plusMinutes(2).format(formatter);
            // First build without signature
            TicketQrData qrDataWithoutSignature = TicketQrData.builder()
                    .ticketId(ticket.getTicketId())
                    .ticketTypeName(ticket.getTicketType().getName())
                    .name(ticket.getName())
                    .validFrom(nowStr)
                    .validUntil(untilStr)
                    .ticketCode(ticket.getTicketCode())
                    .actualPrice(ticket.getActualPrice())
                    .build();

            // Generate signature
            String signature = generateSignature(qrDataWithoutSignature);

            // Create a new instance with signature
            TicketQrData completeQrData = TicketQrData.builder()
                    .ticketId(ticket.getTicketId())
                    .ticketTypeName(ticket.getTicketType().getName())
                    .name(ticket.getName())
                    .validFrom(nowStr)
                    .validUntil(untilStr)
                    .ticketCode(ticket.getTicketCode())
                    .actualPrice(ticket.getActualPrice())
                    .signature(signature)
                    .build();

            return objectMapper.writeValueAsString(completeQrData);
        } catch (JsonProcessingException e) {
            throw new TicketProcessingException("Error generating QR content", e);
        }
    }

    private boolean verifySignature(TicketQrData qrData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        if (qrData == null || qrData.signature() == null) {
            throw new EntityNotFoundException("QR code data or signature is missing");
        }
        if(qrData.validFrom() == null || qrData.validUntil() == null) {
            throw new EntityNotFoundException("QR code data is missing validFrom or validUntil");
        }
        LocalDateTime validUntil = LocalDateTime.parse(qrData.validUntil(), formatter);
        if (validUntil.isBefore(LocalDateTime.now())) {
            throw new TicketProcessingException("QR code has expired");
        }
        String expectedSignature = generateSignature(qrData);
        return expectedSignature.equals(qrData.signature());
    }

    @Scheduled(cron = "0 */5 * * * *") // chạy mỗi 5 phút
    @Transactional
    public void expireTickets() {
        LocalDateTime now = LocalDateTime.now();

        List<TicketStatus> statusesToExpire = List.of(TicketStatus.NOT_USED, TicketStatus.USED);

        statusesToExpire.forEach(status -> {
            List<Tickets> expiredTickets = ticketRepository.findByStatusAndValidUntilBefore(status, now);
            if (!expiredTickets.isEmpty()) {
                expiredTickets.forEach(ticket -> ticket.setStatus(TicketStatus.EXPIRED));
                ticketRepository.saveAll(expiredTickets);
                log.info("✅ [{}] EXPIRED {} {} tickets", now, expiredTickets.size(), status);
            }
        });
    }
}
