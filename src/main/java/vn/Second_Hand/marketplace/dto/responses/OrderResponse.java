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
public class OrderResponse {
    int orderId;
    String buyerName;
    String ownerName;
    Date createdAt;
    String totalAmount;
    String address;
    String status;
    String paymentMethod;
    List<OrderDetailResponse> orderDetails;
}
