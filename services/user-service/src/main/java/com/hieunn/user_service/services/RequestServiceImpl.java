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
import com.hieunn.user_service.utils.JwtUtil;
import com.hieunn.user_service.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    RequestRepository requestRepository;
    UserRepository userRepository;
    RequestMapper requestMapper;
    JwtUtil jwtUtil;
    ValidationUtil validationUtil;

    @Override
    public List<RequestDto> findByUserId(Long userId, String token) {
        validationUtil.checkVerifiedUser(userId, token);

        List<Request> requests = requestRepository.findByUser_UserId(userId);
        return requests
                .stream()
                .map(requestMapper::toRequestDto)
                .toList();
    }

    @Override
    @Transactional
    public RequestDto createRequest(RequestCreationRequest requestCreationRequest, String token) {
        Long userId = jwtUtil.extractUserId(token);

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new CustomException(
                    ErrorMessage.USER_NOT_FOUND.getStatus(),
                    ErrorMessage.USER_NOT_FOUND.getMessage());
        }

        List<RequestDto> passRequests = findByUserId(userId, token);
        boolean hasPendingOrApproved = passRequests.stream()
                .anyMatch(r -> r.getRequestStatus() == RequestStatus.PENDING || r.getRequestStatus() == RequestStatus.APPROVED);
        if (hasPendingOrApproved) {
            throw new CustomException(
                    ErrorMessage.ALREADY_REQUESTED.getStatus(),
                    ErrorMessage.ALREADY_REQUESTED.getMessage()
            );
        }

        Request request = Request.builder()
                .content(requestCreationRequest.getContent())
                .studentCardImage(requestCreationRequest.getStudentCardImage())
                .citizenIdentityCardImage(requestCreationRequest.getCitizenIdentityCardImage())
                .user(user.get())
                .endDate(requestCreationRequest.getEndDate())
                .build();

        Request savedRequest = requestRepository.save(request);
        return requestMapper.toRequestDto(savedRequest);
    }

    @Override
    public List<RequestDto> findAll(String token) {
        validationUtil.checkAdmin(token);

        List<Request> requests = requestRepository.findAll();
        return requests
                .stream()
                .map(requestMapper::toRequestDto)
                .toList();
    }

    @Override
    @Transactional
    public void verifyRequest(Long requestId, boolean isApproved, String token) {
        validationUtil.checkAdmin(token);

        Optional<Request> requestById = requestRepository.findById(requestId);
        if (requestById.isEmpty()) {
            throw new CustomException(
                    ErrorMessage.REQUEST_NOT_FOUND.getStatus(),
                    ErrorMessage.REQUEST_NOT_FOUND.getMessage());
        }

        Request request = requestById.get();
        if (!isApproved) {
            request.setRequestStatus(RequestStatus.REJECTED);
        } else {
            request.setRequestStatus(RequestStatus.APPROVED);
            request.setStartDate(LocalDate.now());
            User user = request.getUser();
            user.setStudent(true);
            user.setStudentExpiredDate(request.getEndDate());
            userRepository.save(user);
        }

        requestRepository.save(request);
    }
}
