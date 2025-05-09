package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.CancelOrderRequest;
import vn.Second_Hand.marketplace.dto.responses.CancelledOrderResponse;
import vn.Second_Hand.marketplace.entity.CancelledOrder;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.entity.OrderDetail;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.CancelledOrderMapper;
import vn.Second_Hand.marketplace.repository.CancelledOrderRepository;
import vn.Second_Hand.marketplace.repository.OrderRepository;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IOrderCancellationService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderCancellationServiceImpl implements IOrderCancellationService {
    OrderRepository orderRepository;
    UserRepository userRepository;
    CancelledOrderRepository cancelledOrderRepository;
    CancelledOrderMapper mapper;

    @Override
    public CancelledOrderResponse cancelOrder(CancelOrderRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!"Chờ xác nhận".equals(order.getStatus())) {
            throw new AppException(ErrorCode.ORDER_CANNOT_BE_CANCELLED);
        }

        // Kiểm tra người dùng hiện tại có phải là người mua hàng
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (order.getBuyer().getId() != currentUser.getId()) {
            throw new AppException(ErrorCode.NOT_ORDER_OWNER);
        }


        order.setStatus("Đã hủy");
        orderRepository.save(order);

        CancelledOrder cancelledOrder = CancelledOrder.builder()
                .order(order)
                .buyer(currentUser)
                .reason(request.getReason())
                .build();

        cancelledOrderRepository.save(cancelledOrder);

        return mapper.toCancelledOrderResponse(cancelledOrder);
    }
}
