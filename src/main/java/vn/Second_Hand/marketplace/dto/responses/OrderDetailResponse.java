package vn.Second_Hand.marketplace.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    int productId;
    String productName;
    int quantity;
    String price;
    String currentImages;
}
