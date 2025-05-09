package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.CancelOrderRequest;
import vn.Second_Hand.marketplace.dto.requests.OrderRequest;
import vn.Second_Hand.marketplace.dto.requests.UpdateOrderStatusRequest;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.CancelledOrderResponse;
import vn.Second_Hand.marketplace.dto.responses.OrderResponse;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.service.IOrderCancellationService;
import vn.Second_Hand.marketplace.service.IOrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/orders")
public class OrderController {
    IOrderService orderService;
    IOrderCancellationService orderCancellationService;

    @PostMapping("/add")
    public ApiResponse<?> createOrder(@RequestBody OrderRequest request) {
        return ApiResponse.builder()
                .message(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/my-orders")
    public ApiResponse<List<OrderResponse>> getMyOrders(@RequestParam(required = false) String status)
    {
        return ApiResponse.<List<OrderResponse>>builder()
                .message("Get orders of current user")
                .data(orderService.getOrdersOfCurrentUser(status))
                .build();
    }

    @PutMapping("/{orderId}/status")
    public ApiResponse<?> updateOrderStatus(@PathVariable int orderId,
                                               @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(orderId, request.getStatus());
        return ApiResponse.builder()
                .message("Order status updated successfully.")
                .build();
    }

    @PostMapping("/cancel")
    public ApiResponse<CancelledOrderResponse> cancelOrder(@RequestBody CancelOrderRequest request) {

        return ApiResponse.<CancelledOrderResponse>builder()
                .message("Order has been cancelled successfully.")
                .data(orderCancellationService.cancelOrder(request))
                .build();
    }
}
