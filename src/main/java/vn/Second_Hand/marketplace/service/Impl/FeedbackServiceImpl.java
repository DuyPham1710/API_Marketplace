package vn.Second_Hand.marketplace.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.FeedbackRequest;
import vn.Second_Hand.marketplace.dto.responses.FeedbackResponse;
import vn.Second_Hand.marketplace.entity.Feedback;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.repository.FeedbackRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IFeedbackService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements IFeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public List<FeedbackResponse> getFeedbackResponsesByProductId(int productId) {
        // Lấy danh sách Feedback từ repository
        List<Feedback> feedbacks = feedbackRepository.findByProductProductId(productId);

        // Chuyển đổi sang FeedbackResponse
        return feedbacks.stream().map(feedback -> FeedbackResponse.builder()
                .productId(feedback.getProduct().getProductId())
                .feedbackerId(feedback.getFeedbacker().getId())
                .feedbackerName(feedback.getFeedbacker().getUsername())
                .productName(feedback.getProduct().getProductName())
                .imageFeedbacker(feedback.getFeedbacker().getAvt())
                .star(feedback.getStar())
                .feedback(feedback.getComment() != null ? feedback.getComment() : "")
                .createdAt(feedback.getCreatedAt())
                .build()).collect(Collectors.toList());
    }

    @Override
    public boolean checkFeedbackExists(int orderId, int productId, int buyerId) {
        return feedbackRepository.existsByOrderIdAndProduct_ProductIdAndFeedbacker_Id(orderId, productId, buyerId);
    }

    public void saveFeedback(FeedbackRequest request) {
        // Lấy user & product từ database
        User user = userRepository.findById(request.getFeedbackerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        Feedback feedback = Feedback.builder()
                .feedbacker(user)
                .product(product)
                .orderId(request.getOrderId())
                .star(request.getStar())
                .comment(request.getFeedback())
                .createdAt(new Date())
                .build();

        feedbackRepository.save(feedback);
    }


}

