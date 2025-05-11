package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.DeliveryAddressRequest;
import vn.Second_Hand.marketplace.dto.requests.UpdateDefaultAddressRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.DeliveryAddressResponse;
import vn.Second_Hand.marketplace.service.IDeliveryAddressService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeliveryAddressController {
    IDeliveryAddressService deliveryAddressService;

    @GetMapping
    public ApiResponse<List<DeliveryAddressResponse>> getUserAddresses() {
        return ApiResponse.<List<DeliveryAddressResponse>>builder()
                .message("List delivery addresses")
                .data(deliveryAddressService.getAddressesByUser())
                .build();
    }

    @PostMapping
    public ApiResponse<DeliveryAddressResponse> createAddress(@RequestBody DeliveryAddressRequest request) {
        return ApiResponse.<DeliveryAddressResponse>builder()
                .message("Add address successfully")
                .data(deliveryAddressService.addDeliveryAddress(request))
                .build();
    }

    @PutMapping("/default")
    public ApiResponse<DeliveryAddressResponse> updateDefaultAddress(@RequestBody UpdateDefaultAddressRequest request) {
        return ApiResponse.<DeliveryAddressResponse>builder()
                .message("Update default address successfully")
                .data(deliveryAddressService.updateDefaultAddress(request))
                .build();
    }
}
