package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.ProductMapper;
import vn.Second_Hand.marketplace.repository.ProductImageRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
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
    UserRepository userRepository;

    @Override
    public List<ProductResponse> findRecommendedByCategoryIds(List<Integer> categoryIds) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng có đăng nhập không
        if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            User currentUser = userRepository.findByUsername(username).orElse(null);

            if (currentUser != null) {
                int currentUserId = currentUser.getId();

                // Sử dụng phương thức tìm kiếm mới có thêm điều kiện lọc ownerId
                List<Product> products = productRepository.findByCategory_CategoryIdInAndOwnerIdNotOrderByCreatedAtDesc(
                        categoryIds, currentUserId);

                return products.stream()
                        .map(product -> {
                            List<ProductImage> images = productImageRepository.findByProduct(product);
                            return productMapper.toProductResponse(product, images);
                        }).collect(Collectors.toList());
            }
        }

        // Nếu không đăng nhập hoặc không tìm thấy user, sử dụng phương thức tìm kiếm ban đầu
        List<Product> products = productRepository.findByCategory_CategoryIdInOrderByCreatedAtDesc(categoryIds);

        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                }).collect(Collectors.toList());

    }

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
    public List<ProductResponse> filterProductsByPriceRange(int categoryId, int minPrice, int maxPrice,String keyword) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng có đăng nhập không
        if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            User currentUser = userRepository.findByUsername(username).orElse(null);

            if (currentUser != null) {
                int currentUserId = currentUser.getId();

                // Sử dụng phương thức tìm kiếm có thêm điều kiện lọc ownerId
                List<Product> products = productRepository.findByCategoryIdAndPriceBetweenAndOwnerIdNot(
                        categoryId, minPrice, maxPrice, keyword, currentUserId);

                return products.stream()
                        .map(product -> {
                            List<ProductImage> images = productImageRepository.findByProduct(product);
                            return productMapper.toProductResponse(product, images);
                        }).collect(Collectors.toList());
            }
        }

        // Nếu không đăng nhập hoặc không tìm thấy user, sử dụng phương thức tìm kiếm ban đầu
        List<Product> products = productRepository.findByCategoryIdAndPriceBetween(categoryId, minPrice, maxPrice, keyword);

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng có đăng nhập không (không phải anonymous)
        if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            // Tìm user ID của người đang đăng nhập
            User currentUser = userRepository.findByUsername(username).orElse(null);

            // Nếu user không null (có đăng nhập), lấy sản phẩm mới nhất của người khác
            if (currentUser != null) {
                int currentUserId = currentUser.getId();

                // Bạn cần thêm phương thức này vào ProductRepository
                List<Product> products = productRepository.findByOwnerIdNotOrderByCreatedAtDesc(currentUserId);

                return products.stream()
                        .map(product -> {
                            List<ProductImage> images = productImageRepository.findByProduct(product);
                            return productMapper.toProductResponse(product, images);
                        }).collect(Collectors.toList());
            }
        }

        // Trường hợp user là null hoặc không đăng nhập, lấy tất cả sản phẩm mới nhất
        List<Product> products = productRepository.findAllByOrderByCreatedAtDesc();

        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                }).collect(Collectors.toList());

    }

    @Override
    public List<ProductResponse> getAllProducts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng có đăng nhập không
        if (authentication != null && !"anonymousUser".equals(authentication.getName())) {
            String username = authentication.getName();
            User currentUser = userRepository.findByUsername(username).orElse(null);

            if (currentUser != null) {
                int currentUserId = currentUser.getId();

                // Cần thêm phương thức này vào repository
                List<Product> products = productRepository.findByOwnerIdNot(currentUserId);

                return products.stream()
                        .map(product -> {
                            List<ProductImage> images = productImageRepository.findByProduct(product);
                            return productMapper.toProductResponse(product, images);
                        })
                        .collect(Collectors.toList());
            }
        }

        // Nếu không đăng nhập hoặc không tìm thấy user, lấy tất cả sản phẩm
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
    public List<ProductResponse> searchProductsByName(String keyword) {
        List<Product> products = productRepository.searchByName(keyword);

        return products.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                })
                .collect(Collectors.toList());
    }



}
