package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.FollowRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.ShopResponse;
import vn.Second_Hand.marketplace.service.IShopService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shops")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShopController {
    IShopService shopService;

    @GetMapping
    public ApiResponse<List<ShopResponse>> getAllShops() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<ShopResponse> shops;

        // Check if user is authenticated and not an anonymous user
        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getName().equals("anonymousUser")) {
            shops = shopService.getAllShopsExceptCurrentUser();
        } else {
            shops = shopService.getAllShops();
        }

        return ApiResponse.<List<ShopResponse>>builder()
                .message("List of all shops")
                .data(shops)
                .build();

    }

    @PostMapping("/follow")
    public ApiResponse<String> toggleFollow(@RequestBody FollowRequest request) {
        return ApiResponse.<String>builder()
                .message(shopService.toggleFollow(request))
                .build();
    }

    @GetMapping("/followed-shops")
    public ApiResponse<List<Integer>> getFollowedShopIds() {
        return ApiResponse.<List<Integer>>builder()
                .message("List of followed shop IDs")
                .data(shopService.getFollowedShopIds())
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<ShopResponse> getCurrentUserShop() {
        return ApiResponse.<ShopResponse>builder()
                .message("Current user's shop information")
                .data(shopService.getCurrentUserShop())
                .build();
    }

} 