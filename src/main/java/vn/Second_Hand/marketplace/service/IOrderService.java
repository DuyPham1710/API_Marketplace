package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.OrderRequest;
import vn.Second_Hand.marketplace.dto.responses.OrderResponse;
import vn.Second_Hand.marketplace.entity.Order;

import java.util.List;

public interface IOrderService {
    String createOrder(OrderRequest request);
    List<OrderResponse> getOrdersOfCurrentUser(String status);
    void updateOrderStatus(int orderId, String status);
}
