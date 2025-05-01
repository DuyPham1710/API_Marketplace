package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.entity.FavoriteProduct;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.repository.FavoriteProductRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IFavoriteProductService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteProductServiceImpl implements IFavoriteProductService {
    FavoriteProductRepository favoriteProductRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    @Override
    public String toggleFavorite(int userId, int productId) {
        Optional<FavoriteProduct> existingFavorite = favoriteProductRepository.findByUser_IdAndProduct_ProductId(userId, productId);

        if (existingFavorite.isPresent()) {
            // Đã thích => Hủy thích
            favoriteProductRepository.delete(existingFavorite.get());
            return "Removed from favorites";
        }
        else {
            // Chưa thích => Thêm vào yêu thích
            User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            Product product = productRepository.findById(productId).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            FavoriteProduct favorite = FavoriteProduct.builder()
                    .user(user)
                    .product(product)
                    .build();

            favoriteProductRepository.save(favorite);
            return "Added to favorites";
        }
    }

    @Override
    public List<Integer> getFavoriteProductIds(int userId) {
        return favoriteProductRepository.findProductIdsByUserId(userId);
    }
}
