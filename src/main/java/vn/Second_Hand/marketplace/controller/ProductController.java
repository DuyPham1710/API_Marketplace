package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.entity.Category;
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


    @GetMapping("/products/search")
    public ApiResponse<?> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProductsByName(keyword);
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
       // return new ResponseEntity<ApiResponse<Category>>(new ApiResponse(true, "Các danh mục hiện có", categoryService.getAllCategories()), HttpStatus.OK);
    }

    @GetMapping("/products/category/{categoryId}")
    public ApiResponse<?> getProductsByCategory(@PathVariable int categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        if (products.isEmpty()) {
            return ApiResponse.builder()
                    .message("No products available")
                    .build();
           // return new ResponseEntity<ApiResponse<Product>>(new ApiResponse(true, "Không có sản phẩm nào", null), HttpStatus.OK);
        }
        return ApiResponse.builder()
                .message("List of products by category")
                .data(productService.getProductsByCategory(categoryId))
                .build();
     //   return new ResponseEntity<ApiResponse<Product>>(new ApiResponse(true, "Danh sách sản phẩm theo danh mục", productService.getProductsByCategory(maDanhMuc)), HttpStatus.OK);
    }

    @GetMapping("/products/top-10")
    public ApiResponse<?> getTop10BestSellingProducts() {
        return ApiResponse.builder()
                .message("Top 10 best selling products")
                .data(productService.getTop10BestSellingProducts())
                .build();
      //  return new ResponseEntity<ApiResponse<Product>>(new ApiResponse(true, "Top 10 sản phẩm bán chạy nhất", productService.getTop10BestSellingProducts()), HttpStatus.OK);
    }

    @GetMapping("/products/last-7-days")
    public ApiResponse<?> getProductsCreatedLast7Days() {
        return ApiResponse.builder()
                .message("Top 10 products created <= 7 days")
                .data(productService.getProductsCreatedLast7Days())
                .build();
      //  return new ResponseEntity<ApiResponse<Product>>(new ApiResponse(true, "Top 10 sản phẩm được tạo <=7 ngày", productService.getProductsCreatedLast7Days()), HttpStatus.OK);
    }

}
