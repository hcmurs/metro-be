package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.RequestCreationRequest;
import com.hieunn.user_service.dtos.responses.RequestDto;
import com.hieunn.user_service.exceptions.CustomException;
import com.hieunn.user_service.exceptions.ErrorMessage;
import com.hieunn.user_service.mappers.RequestMapper;
import com.hieunn.user_service.models.Request;
import com.hieunn.user_service.models.Request.RequestStatus;
import com.hieunn.user_service.models.User;
import com.hieunn.user_service.repositories.RequestRepository;
import com.hieunn.user_service.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    RequestRepository requestRepository;
    UserRepository userRepository;
    RequestMapper requestMapper;
    UserService userService;

    @Override
    @PreAuthorize("authentication.principal.userId == #userId or hasRole('ADMIN')")
    public List<RequestDto> findByUserId(Long userId) {
        List<Request> requests = requestRepository.findByUser_UserId(userId);

        return requests
                .stream()
                .sorted(Comparator.comparing(Request::getCreatedAt).reversed())
                .map(requestMapper::toRequestDto)
                .toList();
    }

    @Override
    @Transactional
    public RequestDto create(RequestCreationRequest requestCreationRequest) {
        User user = userService.getCurrentUser();

        List<Request> passRequests = requestRepository.findByUser_UserId(user.getUserId());
        boolean hasPendingOrApproved = passRequests.stream()
                .anyMatch(r -> r.getRequestStatus() == RequestStatus.PENDING
                        || r.getRequestStatus() == RequestStatus.APPROVED);
        if (hasPendingOrApproved) {
            throw new CustomException(
                    ErrorMessage.ALREADY_REQUESTED.getStatus(),
                    ErrorMessage.ALREADY_REQUESTED.getMessage()
            );
        }

        int requestOrder = passRequests.size() + 1;
        Request request = Request.builder()
                .content(requestCreationRequest.getContent())
                .title("Yêu cầu #" + requestOrder)
                .citizenIdNumber(requestCreationRequest.getCitizenIdNumber())
                .studentCardImage(requestCreationRequest.getStudentCardImage())
                .citizenIdentityCardImage(requestCreationRequest.getCitizenIdentityCardImage())
                .user(user)
                .endDate(requestCreationRequest.getEndDate())
                .build();

        Request savedRequest = requestRepository.save(request);
        return requestMapper.toRequestDto(savedRequest);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<RequestDto> findAll() {
        List<Request> requests = requestRepository.findAll();
        return requests
                .stream()
                .map(requestMapper::toRequestDto)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public RequestDto verify(Long requestId, boolean isApproved, String rejectionReason) {
        Optional<Request> requestById = requestRepository.findById(requestId);
        if (requestById.isEmpty()) {
            throw new CustomException(
                    ErrorMessage.REQUEST_NOT_FOUND.getStatus(),
                    ErrorMessage.REQUEST_NOT_FOUND.getMessage());
        }

        Request request = requestById.get();
        if (!isApproved) {
            request.setRequestStatus(RequestStatus.REJECTED);
            request.setRejectionReason(rejectionReason);
        } else {
            request.setRequestStatus(RequestStatus.APPROVED);
            request.setStartDate(LocalDate.now());
            User user = request.getUser();
            user.setStudent(true);
            user.setStudentExpiredDate(request.getEndDate());
            userRepository.save(user);
        }

        return requestMapper.toRequestDto(requestRepository.save(request));
    }
}
