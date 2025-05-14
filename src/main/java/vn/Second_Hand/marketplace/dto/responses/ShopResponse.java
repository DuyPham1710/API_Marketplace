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
    int id;
    String avt;
    String username;
    int totalReviews;
    double averageRating;
    List<Integer> followerIds;
    List<Integer> followingIds;
    List<ProductResponse> products;

} 