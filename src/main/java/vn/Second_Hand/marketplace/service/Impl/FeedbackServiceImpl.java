package vn.Second_Hand.marketplace.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.responses.FeedbackResponse;
import vn.Second_Hand.marketplace.entity.Feedback;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.repository.FeedbackRepository;
import vn.Second_Hand.marketplace.service.IFeedbackService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;

    @Override
    public List<FeedbackResponse> getFeedbackResponsesByProductId(int productId) {
        // Lấy danh sách Feedback từ repository
        List<Feedback> feedbacks = feedbackRepository.findByProductProductId(productId);

        // Chuyển đổi sang FeedbackResponse
        return feedbacks.stream().map(feedback -> FeedbackResponse.builder()
                .productId(feedback.getProduct().getProductId())
                .FeedbackerId(feedback.getFeedbacker().getId())
                .FeedbackerName(feedback.getFeedbacker().getUsername())
                .productName(feedback.getProduct().getProductName())
                .imageFeedbacker(feedback.getFeedbacker().getAvt())
                .star(feedback.getStar())
                .feedback(feedback.getComment() != null ? feedback.getComment() : "")
                .createdAt(feedback.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

}

