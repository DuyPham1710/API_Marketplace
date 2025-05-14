package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.Second_Hand.marketplace.entity.Feedback;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByProductProductId(int productId);
    boolean existsByOrderIdAndProduct_ProductIdAndFeedbacker_Id(int orderId, int productId, int feedbackerId);

    @Query("SELECT COUNT(f) FROM Feedback f JOIN f.product p WHERE p.ownerId = :ownerId")
    int countByProductOwnerId(@Param("ownerId") int ownerId);

    @Query("SELECT AVG(f.star) FROM Feedback f JOIN f.product p WHERE p.ownerId = :ownerId")
    Double getAverageRatingByProductOwnerId(@Param("ownerId") int ownerId);

}
