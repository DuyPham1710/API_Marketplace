package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.CartRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.CartResponse;
import vn.Second_Hand.marketplace.service.ICartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {
    ICartService cartService;

    @GetMapping
    public ApiResponse<?> getCartItems() {
        return ApiResponse.builder()
                .message("Cart Item")
                .data(cartService.getAllCartItems())
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<?> addToCart(@RequestBody CartRequest request) {
        return ApiResponse.builder()
                .message(cartService.addToCart(request))
                .build();
    }
    @PutMapping("/update")
    public ApiResponse<?> updateCart(@RequestBody CartRequest request) {
        return ApiResponse.builder()
                .message(cartService.updateCart(request))
                .build();
    }

    @DeleteMapping("/remove")
    public ApiResponse<?> removeFromCart(@RequestBody CartRequest request) {
        return ApiResponse.builder()
                .message(cartService.removeFromCart(request))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<?> getCartItemsByUserId(@PathVariable int userId) {
        return ApiResponse.builder()
                .message("Cart items of user with ID: " + userId)
                .data(cartService.getCartItemsByUserId(userId)) // Lấy dữ liệu từ service
                .build();
    }
}
