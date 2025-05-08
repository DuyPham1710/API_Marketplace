package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.ReviewRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.service.IReviewService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/reviews")
public class ReviewController {
    IReviewService reviewService;

    @PutMapping("/update")
    public ApiResponse<?> createReview(@RequestBody ReviewRequest request) {
        return ApiResponse.builder()
                .message(reviewService.updateReview(request))
                .build();
    }

}
