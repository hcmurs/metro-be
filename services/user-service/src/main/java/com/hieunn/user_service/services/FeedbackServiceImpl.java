package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.responses.FeedbackDto;
import com.hieunn.user_service.exceptions.CustomException;
import com.hieunn.user_service.exceptions.ErrorMessage;
import com.hieunn.user_service.mappers.FeedbackMapper;
import com.hieunn.user_service.mappers.RequestMapper;
import com.hieunn.user_service.models.Feedback;
import com.hieunn.user_service.models.User;
import com.hieunn.user_service.repositories.FeedbackRepository;
import com.hieunn.user_service.repositories.RequestRepository;
import com.hieunn.user_service.repositories.UserRepository;
import com.hieunn.user_service.utils.JwtUtil;
import com.hieunn.user_service.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    FeedbackRepository feedbackRepository;
    UserRepository userRepository;
    FeedbackMapper feedbackMapper;
    JwtUtil jwtUtil;
    ValidationUtil validationUtil;

    @Override
    public List<FeedbackDto> findByUser(Long userId, String token) {
        validationUtil.checkVerifiedUser(userId, token);

        List<Feedback> feedbacks = feedbackRepository.findByUser_UserId(userId);
        return feedbacks
                .stream()
                .map(feedbackMapper::toFeedbackDto)
                .toList();
    }

    @Override
    public FeedbackDto createFeedback(FeedbackCreationRequest feedbackCreationRequest, String token) {
        Long userId = jwtUtil.extractUserId(token);

        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new CustomException(
                    ErrorMessage.USER_NOT_FOUND.getStatus(),
                    ErrorMessage.USER_NOT_FOUND.getMessage()
            );
        }

        Feedback feedback = Feedback.builder()
                .category(feedbackCreationRequest.getCategory())
                .content(feedbackCreationRequest.getContent())
                .image(feedbackCreationRequest.getImage())
                .build();

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toFeedbackDto(savedFeedback);
    }

    @Override
    public List<FeedbackDto> findAll(String token) {
        validationUtil.checkAdmin(token);

        List<Feedback> feedbacks = feedbackRepository.findAll();
        return feedbacks
                .stream()
                .map(feedbackMapper::toFeedbackDto)
                .toList();
    }
}
