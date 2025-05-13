package vn.Second_Hand.marketplace.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopResponse {
    String avt;
    String username;
    int totalReviews;
    double averageRating;
    List<Integer> followerIds;
    List<ProductResponse> products;

} 