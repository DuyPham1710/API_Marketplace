package vn.Second_Hand.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.FeedbackRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.FeedbackResponse;
import vn.Second_Hand.marketplace.entity.Feedback;
import vn.Second_Hand.marketplace.service.IFeedbackService;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbacksByProductId(@PathVariable int productId) {
        List<FeedbackResponse> feedbacks = feedbackService.getFeedbackResponsesByProductId(productId);
        ApiResponse<List<FeedbackResponse>> response = ApiResponse.<List<FeedbackResponse>>builder()
                .message("Get feedback successfully")
                .data(feedbacks)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/checkFeedback")
    public ResponseEntity<Boolean> checkFeedbackExists(
            @RequestParam int orderId,
            @RequestParam int productId,
            @RequestParam int buyerId) {
        boolean exists = feedbackService.checkFeedbackExists(orderId, productId, buyerId);
        return ResponseEntity.ok(exists);
    }

    @PostMapping("/saveFeedback")
    public ResponseEntity<ApiResponse<String>> createFeedback(@RequestBody FeedbackRequest request) {
        try {
            feedbackService.saveFeedback(request);

            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .code(1000)
                            .message("Đánh giá đã được lưu")
                            .data("OK")
                            .build()
            );

        } catch (RuntimeException e) {
            return ResponseEntity.ok(
                    ApiResponse.<String>builder()
                            .code(400)
                            .message("Lỗi: " + e.getMessage())
                            .data("error")
                            .build());
        }
    }

    @GetMapping("/given")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbackGivenByCurrentUser() {
        List<FeedbackResponse> feedbacks = feedbackService.getFeedbackGivenByCurrentUser();
        ApiResponse<List<FeedbackResponse>> response = ApiResponse.<List<FeedbackResponse>>builder()
                .message("Feedback given by current user")
                .data(feedbacks)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/received")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getFeedbackReceivedByCurrentUser() {
        List<FeedbackResponse> feedbacks = feedbackService.getFeedbackReceivedByCurrentUser();
        ApiResponse<List<FeedbackResponse>> response = ApiResponse.<List<FeedbackResponse>>builder()
                .message("Feedback received by current user")
                .data(feedbacks)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<FeedbackResponse>>> getAllUserRelatedFeedback() {
        List<FeedbackResponse> feedbacks = feedbackService.getAllUserRelatedFeedback();
        ApiResponse<List<FeedbackResponse>> response = ApiResponse.<List<FeedbackResponse>>builder()
                .message("All feedback related to current user")
                .data(feedbacks)
                .build();
        return ResponseEntity.ok(response);
    }

}