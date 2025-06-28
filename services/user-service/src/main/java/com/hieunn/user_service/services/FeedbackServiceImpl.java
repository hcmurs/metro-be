package com.hieunn.user_service.services;

import com.hieunn.user_service.dtos.requests.FeedbackCreationRequest;
import com.hieunn.user_service.dtos.responses.FeedbackDto;
import com.hieunn.user_service.exceptions.CustomException;
import com.hieunn.user_service.exceptions.ErrorMessage;
import com.hieunn.user_service.mappers.FeedbackMapper;
import com.hieunn.user_service.models.Feedback;
import com.hieunn.user_service.models.User;
import com.hieunn.user_service.repositories.FeedbackRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    FeedbackRepository feedbackRepository;
    UserService userService;
    FeedbackMapper feedbackMapper;

    @Override
    @PreAuthorize("authentication.principal.userId == #userId or hasRole('ADMIN')")
    public List<FeedbackDto> findByUserId(Long userId) {
        List<Feedback> feedbacks = feedbackRepository.findByUser_UserId(userId);

        return feedbacks
                .stream()
                .sorted(Comparator.comparing(Feedback::getCreatedAt).reversed())
                .map(feedbackMapper::toFeedbackDto)
                .toList();
    }

    @Override
    @Transactional
    public FeedbackDto create(FeedbackCreationRequest feedbackCreationRequest) {
        User user = userService.getCurrentUser();

        Feedback feedback = Feedback.builder()
                .category(feedbackCreationRequest.getCategory())
                .content(feedbackCreationRequest.getContent())
                .image(feedbackCreationRequest.getImage())
                .user(user)
                .build();

        feedbackRepository.save(feedback);

        return feedbackMapper.toFeedbackDto(feedback);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<FeedbackDto> findAll() {
        List<Feedback> feedbacks = feedbackRepository.findAll();

        return feedbacks
                .stream()
                .map(feedbackMapper::toFeedbackDto)
                .toList();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public FeedbackDto reply(Long feedbackId, String content) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new CustomException(
                        ErrorMessage.FEEDBACK_NOT_FOUND.getStatus(),
                        ErrorMessage.FEEDBACK_NOT_FOUND.getMessage()));

        feedback.setReply(content);

        feedbackRepository.save(feedback);

        return feedbackMapper.toFeedbackDto(feedback);
    }
}
