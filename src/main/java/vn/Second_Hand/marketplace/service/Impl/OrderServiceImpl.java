package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.OrderDetailRequest;
import vn.Second_Hand.marketplace.dto.requests.OrderRequest;
import vn.Second_Hand.marketplace.dto.responses.OrderResponse;
import vn.Second_Hand.marketplace.entity.*;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.OrderDetailMapper;
import vn.Second_Hand.marketplace.mapper.OrderMapper;
import vn.Second_Hand.marketplace.repository.*;
import vn.Second_Hand.marketplace.service.IOrderService;
import vn.Second_Hand.marketplace.service.IVoucherService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements IOrderService {
    OrderRepository orderRepository;
  //  OrderDetailRepository orderDetailRepository;
    UserRepository userRepository;
    ProductRepository productRepository;
    OrderMapper orderMapper;
    OrderDetailMapper orderDetailMapper;
    ProductImageRepository productImageRepository;
    ReviewRepository reviewRepository;
    IVoucherService voucherService;

    @Override
    public void updateOrderStatus(int orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setStatus(status);
        orderRepository.save(order);

        // Nếu trạng thái là "Đã giao" thì tạo review mặc định cho từng sản phẩm
        if ("Đã giao".equalsIgnoreCase(status)) {
            User buyer = order.getBuyer();

            for (OrderDetail detail : order.getOrderDetails()) {
                Product product = detail.getProduct();

                // Cập nhật số lượng sản phẩm
             //   int currentQuantity = product.getQuantity();
                int orderedQuantity = detail.getQuantity();
            //    int newQuantity = currentQuantity - orderedQuantity;

                // Cập nhật số lượng đã bán cho sản phẩm
                int currentSold = product.getSold();
                int newSold = currentSold + orderedQuantity;
                product.setSold(newSold);


                // Cập nhật số lượng mới cho sản phẩm
           //     product.setQuantity(newQuantity);
                productRepository.save(product);

//                boolean alreadyExists = reviewRepository.existsByOrderDetail_OrderDetailId(detail.getOrderDetailId());
//                if (!alreadyExists || ) {
                    Review review = Review.builder()
                            .buyer(buyer)
                            .product(product)
                            .orderDetail(detail)
                            .comment("")
                            .stars(0)
                            .reviewDate(new Date())
                            .status("Chưa đánh giá")
                            .build();

                    reviewRepository.save(review);
             //   }
            }
        }
    }

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

            // Kiểm tra xem còn đủ số lượng sản phẩm không
            if (product.getQuantity() < d.getQuantity()) {
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);
            }

            OrderDetail detail = orderDetailMapper.toOrderDetail(d);
            detail.setOrder(order);
            detail.setProduct(product);

            int itemTotal = Integer.parseInt(d.getPrice()) * d.getQuantity();
            total += itemTotal;

            details.add(detail);

            // Cập nhật số lượng sản phẩm
            int currentQuantity = product.getQuantity();
            int orderedQuantity = d.getQuantity();
            int newQuantity = currentQuantity - orderedQuantity;
            product.setQuantity(newQuantity);
            productRepository.save(product);

        }

        order.setTotalAmount(String.valueOf(total));
        order.setOrderDetails(details);

        // Áp dụng voucher nếu có
        if (request.getVoucherCode() != null && !request.getVoucherCode().isEmpty()) {
            try {
                voucherService.applyVoucherToOrder(order, request.getVoucherCode());
            } catch (Exception e) {
                // Log lỗi nhưng vẫn tiếp tục xử lý đơn hàng nếu voucher không hợp lệ
                log.error("Error applying voucher: {}", e.getMessage());
            }


        }

        orderRepository.save(order);
        return "Order placed successfully";
    }

    @Override
    public List<OrderResponse> getOrdersOfCurrentUser(String status) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderRepository.findByBuyerAndStatusOrderByCreatedAtDesc(currentUser, status);
        } else {
            orders = orderRepository.findByBuyerOrderByCreatedAtDesc(currentUser);
        }

      //  List<Order> orders = orderRepository.findByBuyerAndStatusOrderByCreatedAtDesc(currentUser, status);

        return orderMapper.toOrderResponses(orders, productImageRepository, userRepository);
    }
}
