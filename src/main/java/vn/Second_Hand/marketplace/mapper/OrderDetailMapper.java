package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.Second_Hand.marketplace.dto.requests.OrderDetailRequest;
import vn.Second_Hand.marketplace.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    @Mapping(target = "orderDetailId", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    OrderDetail toOrderDetail(OrderDetailRequest request);
}
