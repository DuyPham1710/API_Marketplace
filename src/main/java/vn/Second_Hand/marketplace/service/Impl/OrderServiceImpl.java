package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.OrderDetailRequest;
import vn.Second_Hand.marketplace.dto.requests.OrderRequest;
import vn.Second_Hand.marketplace.dto.responses.OrderResponse;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.entity.OrderDetail;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.OrderDetailMapper;
import vn.Second_Hand.marketplace.mapper.OrderMapper;
import vn.Second_Hand.marketplace.repository.*;
import vn.Second_Hand.marketplace.service.IOrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {
    OrderRepository orderRepository;
  //  OrderDetailRepository orderDetailRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    OrderMapper orderMapper;
    OrderDetailMapper orderDetailMapper;
    ProductImageRepository productImageRepository;

    @Override
    public String createOrder(OrderRequest request) {
        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Order order = orderMapper.toOrder(request);
        order.setBuyer(buyer);

        List<OrderDetail> details = new ArrayList<>();
        int total = 0;

        for (OrderDetailRequest d : request.getOrderDetails()) {
            Product product = productRepository.findById(d.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            OrderDetail detail = orderDetailMapper.toOrderDetail(d);
            detail.setOrder(order);
            detail.setProduct(product);

            int itemTotal = Integer.parseInt(d.getPrice()) * d.getQuantity();
            total += itemTotal;

            details.add(detail);
        }

        order.setTotalAmount(String.valueOf(total));
        order.setOrderDetails(details);
        orderRepository.save(order);
        return "Order placed successfully";
    }

    @Override
    public List<OrderResponse> getOrdersOfCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Order> orders = orderRepository.findByBuyerOrderByCreatedAtDesc(currentUser);

        return orderMapper.toOrderResponses(orders, productImageRepository, userRepository);
    }
}
