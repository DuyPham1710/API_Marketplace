package vn.Second_Hand.marketplace.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    int voucherId;
    String code;
    String description;
    String discountType;
    Double discountValue;
    Double minimumOrderAmount;
    Double maximumDiscountAmount;
    Date startDate;
    Date endDate;
    String status;
    Integer quantity;
    Integer usedCount;
    UserResponse shopOwner;
}
