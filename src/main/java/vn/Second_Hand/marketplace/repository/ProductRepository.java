package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory_CategoryIdInOrderByCreatedAtDesc(List<Integer> categoryIds);


    List<Product> findByCategory_CategoryId(int categoryId);

    @Query("SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 10")
    List<Product> findTop10BestSellers();

    @Query("SELECT p FROM Product p WHERE p.createdAt >= :sevenDaysAgo ORDER BY p.createdAt DESC LIMIT 10")
    List<Product> findProductsCreatedLast7Days(@Param("sevenDaysAgo") Date sevenDaysAgo);

    @Query("SELECT p FROM Product p WHERE LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Product> searchByName(@Param("keyword") String keyword);
    List<Product> findAllByOrderByCreatedAtDesc();

    @Query("SELECT MAX(CAST(p.currentPrice AS double)) FROM Product p WHERE p.category.categoryId = :categoryId")
    int findMaxPriceByCategory(@Param("categoryId") int categoryId);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId AND CAST(p.currentPrice AS double) BETWEEN :minPrice AND :maxPrice")
    List<Product> findByCategoryIdAndPriceBetween(@Param("categoryId") int categoryId,
                                                  @Param("minPrice") double minPrice,
                                                  @Param("maxPrice") double maxPrice);

    List<Product> findByOwnerIdOrderByCreatedAtDesc(int ownerId);


}
