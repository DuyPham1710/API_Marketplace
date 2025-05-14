package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.Second_Hand.marketplace.entity.Feedback;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByProductProductId(int productId);
    boolean existsByOrderIdAndProduct_ProductIdAndFeedbacker_Id(int orderId, int productId, int feedbackerId);
}
