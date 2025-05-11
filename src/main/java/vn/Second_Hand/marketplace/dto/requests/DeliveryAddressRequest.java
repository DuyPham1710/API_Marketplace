package vn.Second_Hand.marketplace.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryAddressRequest {
    String addressName;
    String phoneNumber;
    int defaultFlag;
}
