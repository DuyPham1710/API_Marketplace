package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    boolean existsByOrderDetail_OrderDetailId(int orderDetailId); // Không cho đánh giá trùng
    Optional<Review> findByOrderDetail_OrderDetailId(int orderDetailId);

    @Query("SELECT COUNT(r) FROM Review r JOIN r.orderDetail od JOIN od.product p WHERE p.ownerId = :ownerId")
    int countByProductOwnerId(@Param("ownerId") int ownerId);

    @Query("SELECT AVG(r.stars) FROM Review r JOIN r.orderDetail od JOIN od.product p WHERE p.ownerId = :ownerId")
    Double getAverageRatingByProductOwnerId(@Param("ownerId") int ownerId);
}