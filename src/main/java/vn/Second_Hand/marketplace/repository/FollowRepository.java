package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.Follow;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    Optional<Follow> findByFollowerIdAndShopId(int followerId, int shopId);
    
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.shop.id = :shopId")
    int countByShopId(@Param("shopId") int shopId);

    List<Follow> findByFollower(User follower);
    List<Follow> findByShop(User shop);

} 