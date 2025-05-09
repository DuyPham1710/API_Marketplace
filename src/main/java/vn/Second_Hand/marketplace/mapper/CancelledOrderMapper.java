package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.Second_Hand.marketplace.dto.responses.CancelledOrderResponse;
import vn.Second_Hand.marketplace.entity.CancelledOrder;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CancelledOrderMapper {
    @Mapping(source = "buyer.id", target = "buyerId")
    @Mapping(source = "buyer.fullName", target = "buyerName")
    @Mapping(source = "order.orderId", target = "orderId")
    CancelledOrderResponse toCancelledOrderResponse(CancelledOrder cancelledOrder);

    List<CancelledOrderResponse> toCancelledOrderResponses(List<CancelledOrder> cancelledOrders);

}
