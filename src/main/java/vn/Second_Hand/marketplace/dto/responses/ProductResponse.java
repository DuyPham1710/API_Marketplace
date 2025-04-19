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
public class ProductResponse {
    int productId;
    String productName;
    String currentPrice;
    String originalPrice;
    String origin;
    String warranty;
    String productCondition;
    String productDescription;
    String conditionDescription;
    Date createdAt;
    int sold;
    String categoryName;
    List<String> initialImages;
    List<String> currentImages;
}
