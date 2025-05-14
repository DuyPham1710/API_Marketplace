package vn.Second_Hand.marketplace.dto.requests;

import java.util.Date;

public class FeedbackRequest {
    int productId;
    int orderId;
    int feedbackerId;
    int star;
    String feedback;

    public FeedbackRequest() {
    }
    public FeedbackRequest(int productId, int orderId, int feedbackerId, int star, String feedback) {
        this.productId = productId;
        this.orderId = orderId;
        this.feedbackerId = feedbackerId;
        this.star = star;
        this.feedback = feedback;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getFeedbackerId() {
        return feedbackerId;
    }

    public void setFeedbackerId(int feedbackerId) {
        this.feedbackerId = feedbackerId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
