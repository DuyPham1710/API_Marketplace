package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.FavoriteProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
    @Query("SELECT f.product.id FROM FavoriteProduct f WHERE f.user.id = :userId")
    List<Integer> findProductIdsByUserId(@Param("userId") int userId);
    Optional<FavoriteProduct> findByUser_IdAndProduct_ProductId(int userId, int productId);
    void deleteByUser_IdAndProduct_ProductId(int userId, int productId);
//    Optional<FavoriteProduct> findByBuyerIdAndProductId(int buyerId, int productId);
//    List<FavoriteProduct> findByBuyerId(int buyerId);
//
//    @Query("SELECT COUNT(DISTINCT fp.buyer.id) FROM FavoriteProduct fp JOIN fp.product p WHERE p.ownerId = :ownerId")
//    int countDistinctUsersByProductOwnerId(@Param("ownerId") int ownerId);
}
