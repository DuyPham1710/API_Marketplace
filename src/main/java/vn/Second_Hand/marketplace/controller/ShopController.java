package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
        return ApiResponse.<List<ShopResponse>>builder()
                .message("List of all shops")
                .data(shopService.getAllShopsExceptCurrentUser())
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
} 