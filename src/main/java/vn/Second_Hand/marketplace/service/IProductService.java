package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Product;

import java.util.List;

public interface IProductService {
    List<ProductResponse> getProductsByCategory(int maDanhMuc);
    List<Product> getTop10BestSellingProducts();
    List<Product> getProductsCreatedLast7Days();
    List<Product> searchProductsByName(String keyword);
    ProductResponse getProductWithImages(int productId);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getAllProductsOrderByNewest();
    int getMaxPriceByCategory(int categoryId);
    List<ProductResponse> filterProductsByPriceRange(int categoryId, int minPrice, int maxPrice);
    List<ProductResponse> getProductsByProductIds(List<Integer> productIds);
    List<ProductResponse> findRecommendedByCategoryIds(List<Integer> categoryIds);
}
