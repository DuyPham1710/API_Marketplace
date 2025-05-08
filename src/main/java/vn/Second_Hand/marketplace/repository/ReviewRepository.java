package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.Second_Hand.marketplace.entity.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByOrderDetail_OrderDetailId(int orderDetailId); // Không cho đánh giá trùng
    Optional<Review> findByOrderDetail_OrderDetailId(int orderDetailId);
}