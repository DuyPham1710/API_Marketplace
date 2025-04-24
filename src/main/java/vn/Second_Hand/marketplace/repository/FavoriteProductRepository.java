package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.FavoriteProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Integer> {
    List<FavoriteProduct> findByUser_Id(int userId);
    Optional<FavoriteProduct> findByUser_IdAndProduct_ProductId(int userId, int productId);
    void deleteByUser_IdAndProduct_ProductId(int userId, int productId);
}
