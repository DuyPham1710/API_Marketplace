package vn.Second_Hand.marketplace.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedbackResponse {
    int productId;
    int feedbackerId;
    String feedbackerName;
    String productName;
    String imageFeedbacker;
    int star;
    String feedback;
    Date createdAt;
}
