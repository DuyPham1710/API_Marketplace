package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.FollowRequest;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.dto.responses.ShopResponse;
import vn.Second_Hand.marketplace.entity.Follow;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.ProductMapper;
import vn.Second_Hand.marketplace.mapper.ShopMapper;
import vn.Second_Hand.marketplace.repository.*;
import vn.Second_Hand.marketplace.service.IShopService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShopServiceImpl implements IShopService {
    UserRepository userRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;
    FeedbackRepository feedbackRepository;
    FollowRepository followRepository;
    ShopMapper shopMapper;
    ProductMapper productMapper;

    @Override
    public ShopResponse getCurrentUserShop() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy số lượng review
        int totalReviews = feedbackRepository.countByProductOwnerId(currentUser.getId());

        // Lấy trung bình rating
        Double avgRating = feedbackRepository.getAverageRatingByProductOwnerId(currentUser.getId());
        double averageRating = avgRating != null ? avgRating : 0.0;
        averageRating = Math.round(averageRating * 10.0) / 10.0;

        // Lấy danh sách ID người theo dõi từ bảng Follow
        List<Integer> followerIds = followRepository.findByShop(currentUser)
                .stream()
                .map(follow -> follow.getFollower().getId())
                .collect(Collectors.toList());

        // Lấy danh sách ID người mà shop đang theo dõi
        List<Integer> followingIds = followRepository.findByFollower(currentUser)
                .stream()
                .map(follow -> follow.getShop().getId())
                .collect(Collectors.toList());


        // Lấy tất cả sản phẩm của shop
        List<ProductResponse> shopProducts = getAllProductsOfShop(currentUser);

        return shopMapper.toShopResponse(currentUser, totalReviews, averageRating, followerIds, followingIds, shopProducts);
    }

    @Override
    public List<ShopResponse> getAllShops() {
        // Lấy danh sách tất cả các user có sản phẩm (shop)
        List<User> shops = userRepository.findAll().stream()
                .filter(user -> !productRepository.findByOwnerIdOrderByCreatedAtDesc(user.getId()).isEmpty())
                .collect(Collectors.toList());

        return shops.stream().map(shop -> {
            // Lấy số lượng review
            int totalReviews = feedbackRepository.countByProductOwnerId(shop.getId());

            // Lấy trung bình rating
            Double avgRating = feedbackRepository.getAverageRatingByProductOwnerId(shop.getId());
            double averageRating = avgRating != null ? avgRating : 0.0;
            averageRating = Math.round(averageRating * 10.0) / 10.0;

            // Lấy danh sách ID người theo dõi từ bảng Follow
            List<Integer> followerIds = followRepository.findByShop(shop)
                    .stream()
                    .map(follow -> follow.getFollower().getId())
                    .collect(Collectors.toList());

            // Lấy danh sách ID người mà shop đang theo dõi
            List<Integer> followingIds = followRepository.findByFollower(shop)
                    .stream()
                    .map(follow -> follow.getShop().getId())
                    .collect(Collectors.toList());

            // Lấy tất cả sản phẩm của shop
            List<ProductResponse> shopProducts = getAllProductsOfShop(shop);

            return shopMapper.toShopResponse(shop, totalReviews, averageRating, followerIds, followingIds, shopProducts);
        }).collect(Collectors.toList());
    }


    @Override
    public List<ShopResponse> getAllShopsExceptCurrentUser() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User currentUser = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Lấy danh sách các user có sản phẩm (shop) trừ user hiện tại
        List<User> shops = userRepository.findUsersWithProductsExcept(currentUser.getId());

        return shops.stream().map(shop -> {
            // Lấy số lượng review
            int totalReviews = feedbackRepository.countByProductOwnerId(shop.getId());
            
            // Lấy trung bình rating
            Double avgRating = feedbackRepository.getAverageRatingByProductOwnerId(shop.getId());
            double averageRating = avgRating != null ? avgRating : 0.0;
            averageRating = Math.round(averageRating * 10.0) / 10.0;
            
            // Lấy danh sách ID người theo dõi từ bảng Follow
            List<Integer> followerIds = followRepository.findByShop(shop)
                    .stream()
                    .map(follow -> follow.getFollower().getId())
                    .collect(Collectors.toList());

            // Lấy danh sách ID người mà shop đang theo dõi
            List<Integer> followingIds = followRepository.findByFollower(shop)
                    .stream()
                    .map(follow -> follow.getShop().getId())
                    .collect(Collectors.toList());

            // Lấy tất cả sản phẩm của shop
            List<ProductResponse> shopProducts = getAllProductsOfShop(shop);

            return shopMapper.toShopResponse(shop, totalReviews, averageRating, followerIds, followingIds, shopProducts);
        }).collect(Collectors.toList());
    }

    @Override
    public String toggleFollow(FollowRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Kiểm tra shop có tồn tại không
        User shop = userRepository.findById(request.getShopId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Kiểm tra xem user có đang follow shop này không
        Optional<Follow> existingFollow = followRepository.findByFollowerIdAndShopId(currentUser.getId(), shop.getId());

        if (existingFollow.isPresent()) {
            // Nếu đã follow thì hủy follow
            followRepository.delete(existingFollow.get());
            return "Unfollow shop successfully";
        } else {
            // Nếu chưa follow thì thêm follow mới
            Follow newFollow = Follow.builder()
                    .follower(currentUser)
                    .shop(shop)
                    .build();
            followRepository.save(newFollow);
            return "Follow shop successfully";
        }
    }

    @Override
    public List<Integer> getFollowedShopIds() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        List<Integer> followedShopIds = followRepository.findByFollower(user)
                .stream()
                .map(follow -> follow.getShop().getId()) // Extract the shop ID from the User entity
                .collect(Collectors.toList());

        return followedShopIds;


    }

    private List<ProductResponse> getAllProductsOfShop(User shopOwner) {
        List<Product> shopProducts = productRepository.findByOwnerIdOrderByCreatedAtDesc(shopOwner.getId());

        return shopProducts.stream()
                .map(product -> {
                    List<ProductImage> images = productImageRepository.findByProduct(product);
                    return productMapper.toProductResponse(product, images);
                }).collect(Collectors.toList());
    }
} 