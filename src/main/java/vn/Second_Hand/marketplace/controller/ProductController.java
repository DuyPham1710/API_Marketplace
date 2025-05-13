package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.service.ICategoryService;
import vn.Second_Hand.marketplace.service.IProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    IProductService productService;
    ICategoryService categoryService;

    @PostMapping("/products/recommend")
    public ApiResponse<List<ProductResponse>> getRecommendedProducts(@RequestBody List<Integer> categoryIds) {
    //    List<ProductResponse> recommendedProducts = productService.findRecommendedByCategoryIds(categoryIds);
        return ApiResponse.<List<ProductResponse>>builder()
                .message("Recommended products fetched")
                .data(productService.findRecommendedByCategoryIds(categoryIds))
                .build();
    }
    @PostMapping("/products/by-ids")
    public ApiResponse<List<ProductResponse>> getProductsByProductIds(@RequestBody List<Integer> productIds) {
        return ApiResponse.<List<ProductResponse>>builder()
                .message("Get products sucessfully")
                .data(productService.getProductsByProductIds(productIds))
                .build();
    }

    @GetMapping("/products/filter")
    public ApiResponse<List<ProductResponse>> filterProductsByPrice(
            @RequestParam int categoryId,
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            @RequestParam String keyword) {

        return ApiResponse.<List<ProductResponse>>builder()
                .message("Filter")
                .data(productService.filterProductsByPriceRange(categoryId, minPrice, maxPrice,keyword))
                .build();
    }
    @GetMapping("/category/{categoryId}/max-price")
    public ApiResponse<Integer> getMaxPrice(@PathVariable("categoryId") int categoryId) {
        return ApiResponse.<Integer>builder()
                .message("Max price by categoryId = " + categoryId)
                .data(productService.getMaxPriceByCategory(categoryId))
                .build();
    }

    @GetMapping("/products/newest")
    public ApiResponse<List<ProductResponse>> getNewestProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .message("New Products")
                .data(productService.getAllProductsOrderByNewest())
                .build();
    }

    @GetMapping("/products")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .message("Products")
                .data(productService.getAllProducts())
                .build();
    }

    @GetMapping("/products/{id}")
    public ApiResponse<ProductResponse> getProductDetails(@PathVariable("id") int id) {
        ProductResponse response = productService.getProductWithImages(id);
        return ApiResponse.<ProductResponse>builder()
                .message("Product Detail with id = " + id)
                .data(response)
                .build();
    }

    @GetMapping("/products/search")
    public ApiResponse<?> searchProducts(@RequestParam String keyword) {
        List<ProductResponse> products = productService.searchProductsByName(keyword);
        if (products.isEmpty()) {
            return ApiResponse.builder()
                    .message("Không tìm thấy sản phẩm")
                    .build();
        }
        return ApiResponse.builder()
                .message("Kết quả tìm kiếm")
                .data(products)
                .build();
    }

    @GetMapping("/categories")
    public ApiResponse<?> getAllCategories() {
        return ApiResponse.builder()
                .message("Categories")
                .data(categoryService.getAllCategories())
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable int categoryId) {
//        List<ProductResponse> products = productService.getProductsByCategory(categoryId);
//        if (products.isEmpty()) {
//            return ApiResponse.<List<ProductResponse>>builder()
//                    .message("No products available")
//                    .build();
//        }
        return ApiResponse.<List<ProductResponse>>builder()
                .message("List of products by category")
                .data(productService.getProductsByCategory(categoryId))
                .build();
    }

    @GetMapping("/products/top-10")
    public ApiResponse<?> getTop10BestSellingProducts() {
        return ApiResponse.builder()
                .message("Top 10 best selling products")
                .data(productService.getTop10BestSellingProducts())
                .build();
    }

    @GetMapping("/products/last-7-days")
    public ApiResponse<?> getProductsCreatedLast7Days() {
        return ApiResponse.builder()
                .message("Top 10 products created <= 7 days")
                .data(productService.getProductsCreatedLast7Days())
                .build();
    }

}
