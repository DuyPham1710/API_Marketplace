package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.Second_Hand.marketplace.dto.responses.DeliveryAddressResponse;
import vn.Second_Hand.marketplace.entity.DeliveryAddress;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {
    @Mapping(source = "buyer.username", target = "username")
    @Mapping(source = "defaultAddress", target = "defaultAddress")
    DeliveryAddressResponse toResponse(DeliveryAddress address);

    List<DeliveryAddressResponse> toResponseList(List<DeliveryAddress> addresses);
}
