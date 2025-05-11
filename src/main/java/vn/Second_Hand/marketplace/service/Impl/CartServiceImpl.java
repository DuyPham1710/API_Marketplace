package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.CartRequest;
import vn.Second_Hand.marketplace.dto.responses.CartResponse;
import vn.Second_Hand.marketplace.dto.responses.ProductResponse;
import vn.Second_Hand.marketplace.dto.responses.UserResponse;
import vn.Second_Hand.marketplace.entity.Cart;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.repository.CartRepository;
import vn.Second_Hand.marketplace.repository.ProductImageRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.ICartService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements ICartService {
    CartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    ProductImageRepository productImageRepository;

    @Override
    public List<Cart> getAllCartItems() {
        return cartRepository.findAll();
    }

    @Override
    public String addToCart(CartRequest request) {
        User user = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        if (cartRepository.existsByBuyerAndProduct(user, product)) {
            // Nếu đã tồn tại, cập nhật số lượng
            Cart cart = cartRepository.findByBuyerAndProduct(user, product)
                    .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
            cart.setQuantity(cart.getQuantity() + request.getQuantity());
            cartRepository.save(cart);
        }
        else {
            // Nếu chưa có, tạo mới
            Cart newCart = Cart.builder()
                    .buyer(user)
                    .product(product)
                    .quantity(request.getQuantity())
                    .build();
            cartRepository.save(newCart);
        }

        return "Product added to cart successfully";
    }

    @Override
    public String updateCart(CartRequest request) {
        User user = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Cart cart = cartRepository.findByBuyerAndProduct(user, product)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        cart.setQuantity(request.getQuantity());
        cartRepository.save(cart);
        return "Cart updated successfully";
    }

    @Override
    public String removeFromCart(CartRequest request) {
        User user = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Cart cart = cartRepository.findByBuyerAndProduct(user, product)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        cartRepository.delete(cart);
        return "Product removed from cart";
    }

    @Override
    public List<CartResponse> getCartItemsByUserId(int userId) {
        List<Cart> cartItems = cartRepository.findByBuyerId(userId);

        return cartItems.stream().map(cart -> {
            // Fetch sản phẩm liên kết với Cart
            Product product = cart.getProduct();

            // Fetch images liên quan đến sản phẩm
            List<ProductImage> productImages = productImageRepository.findByProduct(product);

            // Tách initialImages và currentImages từ ProductImage
            List<String> initialImages = productImages.stream()
                    .map(ProductImage::getInitialImage)
                    .collect(Collectors.toList());

            List<String> currentImages = productImages.stream()
                    .map(ProductImage::getCurrentImage)
                    .collect(Collectors.toList());

            // Xây dựng ProductResponse
            ProductResponse productResponse = ProductResponse.builder()
                    .productId(product.getProductId())
                    .ownerId(product.getOwnerId())
                    .productName(product.getProductName())
                    .currentPrice(product.getCurrentPrice())
                    .originalPrice(product.getOriginalPrice())
                    .origin(product.getOrigin())
                    .warranty(product.getWarranty())
                    .productCondition(product.getProductCondition())
                    .conditionDescription(product.getConditionDescription())
                    .productDescription(product.getProductDescription())
                    .createdAt(product.getCreatedAt())
                    .sold(product.getSold())
                    .quantity(product.getQuantity())
                    .categoryId(product.getCategory().getCategoryId())
                    .categoryName(product.getCategory().getCategoryName())
                    .initialImages(initialImages)
                    .currentImages(currentImages) // Gán danh sách current images
                    .build();

            // Map Buyer
            UserResponse buyer = UserResponse.builder()
                    .fullName(cart.getBuyer().getFullName())
                    .phoneNumber(cart.getBuyer().getPhoneNumber())
                    .gender(cart.getBuyer().getGender())
                    .dateOfBirth(cart.getBuyer().getDateOfBirth())
                    .avt(cart.getBuyer().getAvt())
                    .email(cart.getBuyer().getEmail())
                    .username(cart.getBuyer().getUsername())
                    .isActive(cart.getBuyer().getIsActive())
                    .roles(cart.getBuyer().getRoles())
                    .build();

            // Xây dựng CartResponse
            return CartResponse.builder()
                    .productId(cart.getProduct().getProductId()) // ID sản phẩm
                    .buyer(buyer) // Thông tin người mua
                    .product(productResponse) // Thông tin sản phẩm đã ánh xạ
                    .quantity(cart.getQuantity()) // Số lượng sản phẩm trong giỏ
                    .build();
        }).collect(Collectors.toList());
    }
}
