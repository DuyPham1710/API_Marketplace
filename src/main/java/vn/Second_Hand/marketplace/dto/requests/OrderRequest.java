package vn.Second_Hand.marketplace.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    int buyerId;
    String address;
    String paymentMethod;
    List<OrderDetailRequest> orderDetails;
}
