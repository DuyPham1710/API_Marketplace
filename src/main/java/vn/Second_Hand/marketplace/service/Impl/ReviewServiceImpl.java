package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.ReviewRequest;
import vn.Second_Hand.marketplace.entity.OrderDetail;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.Review;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.repository.OrderDetailRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.repository.ReviewRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IReviewService;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReviewServiceImpl implements IReviewService {
    ReviewRepository reviewRepository;
    ProductRepository productRepository;
    OrderDetailRepository orderDetailRepository;
    UserRepository userRepository;

    @Override
    public String updateReview(ReviewRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User buyer = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Review review = reviewRepository.findByOrderDetail_OrderDetailId(request.getOrderDetailId())
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND));

        if (review.getBuyer().getId() != buyer.getId()) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        review.setComment(request.getComment());
        review.setStars(request.getStars());
        review.setReviewDate(new Date());
        review.setStatus("Đã đánh giá");

        reviewRepository.save(review);

        return "Review submitted successfully.";
    }
}
