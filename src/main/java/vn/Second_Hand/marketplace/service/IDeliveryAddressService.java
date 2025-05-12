package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.DeliveryAddressRequest;
import vn.Second_Hand.marketplace.dto.requests.UpdateDefaultAddressRequest;
import vn.Second_Hand.marketplace.dto.requests.UpdateDeliveryAddressRequest;
import vn.Second_Hand.marketplace.dto.responses.DeliveryAddressResponse;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

public interface IDeliveryAddressService {
    List<DeliveryAddressResponse> getAddressesByUser();
    DeliveryAddressResponse addDeliveryAddress(DeliveryAddressRequest request);
    DeliveryAddressResponse updateDefaultAddress(UpdateDefaultAddressRequest request);
    DeliveryAddressResponse updateDeliveryAddress(UpdateDeliveryAddressRequest request);
}
