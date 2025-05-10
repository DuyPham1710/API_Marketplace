package vn.Second_Hand.marketplace.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.Second_Hand.marketplace.dto.requests.OrderRequest;
import vn.Second_Hand.marketplace.dto.responses.OrderDetailResponse;
import vn.Second_Hand.marketplace.dto.responses.OrderResponse;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.entity.OrderDetail;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.ProductImage;
import vn.Second_Hand.marketplace.repository.ProductImageRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "createdAt", expression = "java(new java.util.Date())")
    @Mapping(target = "status", constant = "Chờ xác nhận")
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "orderDetails", ignore = true)
    Order toOrder(OrderRequest request);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "buyerName", source = "buyer.username")
    @Mapping(target = "ownerName", expression = "java(getOwnerName(order, userRepo))")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "totalAmount", source = "totalAmount")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "paymentMethod", source = "paymentMethod")
    @Mapping(target = "orderDetails", expression = "java(mapOrderDetails(order.getOrderDetails(), imageRepo))")
    OrderResponse toOrderResponse(Order order, @Context ProductImageRepository imageRepo, @Context UserRepository userRepo);

    List<OrderResponse> toOrderResponses(List<Order> orders, @Context ProductImageRepository imageRepo, @Context UserRepository userRepo);


    // Hàm ánh xạ 1 OrderDetail → OrderDetailResponse
    default OrderDetailResponse mapToOrderDetailResponse(OrderDetail detail, @Context ProductImageRepository imageRepo) {
        Product product = detail.getProduct();

        String currentImage = imageRepo.findByProduct(product).stream()
                .findFirst()
                .map(ProductImage::getCurrentImage)
                .orElse(null);

        return OrderDetailResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(detail.getQuantity())
                .price(detail.getPrice())
                .currentImages(currentImage)
                .build();
    }


    // Hàm ánh xạ List<OrderDetail> → List<OrderDetailResponse>
    default List<OrderDetailResponse> mapOrderDetails(List<OrderDetail> details, @Context ProductImageRepository imageRepo) {
        if (details == null) return List.of();
        return details.stream()
                .map(detail -> mapToOrderDetailResponse(detail, imageRepo))
                .toList();
    }

    default String getOwnerName(Order order, UserRepository userRepo) {
        if (order.getOrderDetails() == null || order.getOrderDetails().isEmpty()) return null;

        Product product = order.getOrderDetails().get(0).getProduct();
        if (product == null) return null;

        return userRepo.findById(product.getOwnerId())
                .map(user -> user.getUsername())
                .orElse(null);
    }
}
