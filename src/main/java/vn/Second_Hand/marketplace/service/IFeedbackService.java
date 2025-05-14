package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.responses.FeedbackResponse;
import vn.Second_Hand.marketplace.entity.Feedback;

import java.util.List;

public interface IFeedbackService {
    List<FeedbackResponse> getFeedbackResponsesByProductId(int productId);


}
