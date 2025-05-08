package vn.Second_Hand.marketplace.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewRequest {
    int orderDetailId;
    String comment;
    int stars;
}
