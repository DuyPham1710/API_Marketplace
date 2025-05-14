package vn.Second_Hand.marketplace.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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


}