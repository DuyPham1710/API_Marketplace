package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.ProductMapper;
import vn.Second_Hand.marketplace.repository.ProductImageRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.service.IProductService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements IProductService {
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    ProductMapper productMapper;

    @Override
    public List<ProductResponse> getProductsByProductIds(List<Integer> productIds) {
        List<Product> products = productRepository.findAllById(productIds);

        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                }).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> filterProductsByPriceRange(int categoryId, int minPrice, int maxPrice) {
        List<Product> products = productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice);

        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                }).collect(Collectors.toList());
    }

    @Override
    public int getMaxPriceByCategory(int categoryId) {
        return productRepository.findMaxPriceByCategory(categoryId);
    }
    @Override
    public List<ProductResponse> getAllProductsOrderByNewest() {
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc();

        return products.stream()
                .map(product -> {
            List<ProductImage> images = productImageRepository.findByProduct(product);
            return productMapper.toProductResponse(product, images);
        }).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                })
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductWithImages(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        List<ProductImage> images = productImageRepository.findByProduct(product);

        return productMapper.toProductResponse(product, images);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(int categoryId) {
        List<Product> products = productRepository.findByCategory_CategoryId(categoryId);

        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getTop10BestSellingProducts() {
        return productRepository.findTop10BestSellers();
    }

    @Override
    public List<Product> getProductsCreatedLast7Days() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date sevenDaysAgo = calendar.getTime();
        return productRepository.findProductsCreatedLast7Days(sevenDaysAgo);
    }
    @Override
    public List<Product> searchProductsByName(String keyword) {
        return productRepository.searchByName(keyword);
    }



}
