package vn.Second_Hand.marketplace.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CancelledOrderResponse {
    int cancelId;
    int buyerId;
    String buyerName;
    int orderId;
    String reason;
    Date cancelledAt;
}
