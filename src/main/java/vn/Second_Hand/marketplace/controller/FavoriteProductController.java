package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.FavoriteRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.entity.FavoriteProduct;
import vn.Second_Hand.marketplace.service.IFavoriteProductService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/favorites")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FavoriteProductController {
    IFavoriteProductService favoriteProductService;

    @PostMapping("/toggle")
    public ApiResponse<?> toggleFavorite(@RequestBody FavoriteRequest request) {
        return ApiResponse.builder()
                .message(favoriteProductService.toggleFavorite(request.getUserId(), request.getProductId()))
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<List<Integer>> getFavoriteProductIds(@PathVariable("userId") int userId) {
        return ApiResponse.<List<Integer>>builder()
                .message("User's favorite products")
                .data(favoriteProductService.getFavoriteProductIds(userId))
                .build();
    }
}
