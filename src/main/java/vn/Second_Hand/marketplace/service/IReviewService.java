package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.ReviewRequest;

public interface IReviewService {
    String updateReview(ReviewRequest request);
}
