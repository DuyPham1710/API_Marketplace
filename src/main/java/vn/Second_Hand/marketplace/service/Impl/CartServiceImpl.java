package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.CartRequest;
import vn.Second_Hand.marketplace.entity.Cart;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.repository.CartRepository;
import vn.Second_Hand.marketplace.repository.ProductRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.ICartService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements ICartService {
    CartRepository cartRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

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
}
