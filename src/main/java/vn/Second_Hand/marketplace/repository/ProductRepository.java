package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_CategoryIdInOrderByCreatedAtDesc(List<Integer> categoryIds);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId IN :categoryIds AND p.ownerId != :ownerId ORDER BY p.createdAt DESC")
    List<Product> findByCategory_CategoryIdInAndOwnerIdNotOrderByCreatedAtDesc(
            @Param("categoryIds") List<Integer> categoryIds,
            @Param("ownerId") int ownerId);

    List<Product> findByCategory_CategoryId(int categoryId);

    @Query("SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 10")
    List<Product> findTop10BestSellers();

    @Query("SELECT p FROM Product p WHERE p.createdAt >= :sevenDaysAgo ORDER BY p.createdAt DESC LIMIT 10")
    List<Product> findProductsCreatedLast7Days(@Param("sevenDaysAgo") Date sevenDaysAgo);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByName(@Param("keyword") String keyword);
    List<Product> findAllByOrderByCreatedAtDesc();

    @Query("SELECT MAX(CAST(p.currentPrice AS double)) FROM Product p WHERE (:categoryId = -1 OR p.category.categoryId = :categoryId)")
    int findMaxPriceByCategory(@Param("categoryId") int categoryId);

    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId = -1 OR p.category.categoryId = :categoryId) " +
            "AND (:minPrice < 0 OR CAST(p.currentPrice AS double) BETWEEN :minPrice AND :maxPrice) " +
            "AND (:keyword = 'nullNull1511' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> findByCategoryIdAndPriceBetween(@Param("categoryId") int categoryId,
                                   @Param("minPrice") double minPrice,
                                   @Param("maxPrice") double maxPrice,
                                   @Param("keyword") String keyword);

    @Query("SELECT p FROM Product p " +
            "WHERE (:categoryId = -1 OR p.category.categoryId = :categoryId) " +
            "AND (:minPrice < 0 OR CAST(p.currentPrice AS double) BETWEEN :minPrice AND :maxPrice) " +
            "AND (:keyword = 'nullNull1511' OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND p.ownerId != :ownerId")
    List<Product> findByCategoryIdAndPriceBetweenAndOwnerIdNot(
            @Param("categoryId") int categoryId,
            @Param("minPrice") double minPrice,
            @Param("maxPrice") double maxPrice,
            @Param("keyword") String keyword,
            @Param("ownerId") int ownerId);


    List<Product> findByOwnerIdOrderByCreatedAtDesc(int ownerId);

    @Query("SELECT p FROM Product p WHERE p.ownerId != :ownerId ORDER BY p.createdAt DESC")
    List<Product> findByOwnerIdNotOrderByCreatedAtDesc(@Param("ownerId") int ownerId);

    @Query("SELECT p FROM Product p WHERE p.ownerId != :ownerId")
    List<Product> findByOwnerIdNot(@Param("ownerId") int ownerId);

}
