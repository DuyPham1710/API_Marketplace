package vn.Second_Hand.marketplace.service.Impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.responses.VoucherResponse;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.entity.Voucher;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.VoucherMapper;
import vn.Second_Hand.marketplace.repository.OrderRepository;
import vn.Second_Hand.marketplace.repository.VoucherRepository;
import vn.Second_Hand.marketplace.service.IVoucherService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherServiceImpl implements IVoucherService {
    VoucherRepository voucherRepository;
    VoucherMapper voucherMapper;

    @Override
    public Voucher findByCode(String code) {
        return voucherRepository.findByCode(code)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
    }

    @Override
    public Voucher findById(int voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND));
    }

    @Override
    public boolean isVoucherValid(Voucher voucher, double orderAmount) {
        if (voucher == null) {
            return false;
        }

        Date currentDate = new Date();

        // Kiểm tra ngày hiệu lực
        boolean isDateValid = (voucher.getStartDate().before(currentDate) ||
                voucher.getStartDate().equals(currentDate)) &&
                (voucher.getEndDate().after(currentDate) ||
                        voucher.getEndDate().equals(currentDate));

        // Kiểm tra trạng thái voucher
        boolean isStatusValid = "active".equalsIgnoreCase(voucher.getStatus());

        // Kiểm tra số lượng
        boolean isQuantityValid = voucher.getQuantity() > voucher.getUsedCount();

        // Kiểm tra giá trị đơn hàng tối thiểu
        boolean isMinAmountValid = orderAmount >= voucher.getMinimumOrderAmount();

        return isDateValid && isStatusValid && isQuantityValid && isMinAmountValid;
    }

    @Override
    public double calculateDiscount(Voucher voucher, double orderAmount) {
        if (voucher == null) {
            return 0;
        }

        double discount = 0;

        if ("percentage".equalsIgnoreCase(voucher.getDiscountType())) {
            // Tính giảm giá theo phần trăm
            discount = orderAmount * (voucher.getDiscountValue() / 100);

            // Áp dụng giới hạn giảm giá tối đa nếu có
            if (voucher.getMaximumDiscountAmount() != null &&
                    discount > voucher.getMaximumDiscountAmount()) {
                discount = voucher.getMaximumDiscountAmount();
            }
        } else if ("fixed".equalsIgnoreCase(voucher.getDiscountType())) {
            // Giảm giá cố định
            discount = voucher.getDiscountValue();

            // Đảm bảo không giảm quá giá trị đơn hàng
            if (discount > orderAmount) {
                discount = orderAmount;
            }
        }

        return discount;
    }

    @Override
    public void applyVoucherToOrder(Order order, String voucherCode) {
        if (voucherCode == null || voucherCode.trim().isEmpty()) {
            return;
        }

        Voucher voucher = findByCode(voucherCode);

        // Lấy shop owner ID từ sản phẩm đầu tiên trong đơn hàng (giả sử đơn hàng chỉ có sản phẩm từ một shop)
        int shopOwnerId = order.getOrderDetails().get(0).getProduct().getOwnerId();

        // Kiểm tra voucher có thuộc về shop này không
        if (voucher.getShopOwner().getId() != shopOwnerId) {
            return;
        }
        System.out.println("Voucher code: " + voucherCode);
        double orderAmount = Double.parseDouble(order.getTotalAmount());

        if (isVoucherValid(voucher, orderAmount)) {
            double discount = calculateDiscount(voucher, orderAmount);
            double finalAmount = orderAmount - discount;

            // Cập nhật thông tin đơn hàng
            order.setVoucher(voucher);
            order.setDiscountAmount(String.valueOf(discount));
            order.setTotalAmount(String.valueOf(finalAmount));

            // Cập nhật thông tin voucher
            voucher.setUsedCount(voucher.getUsedCount() + 1);
            voucher.setQuantity(voucher.getQuantity() - 1);

            // Kiểm tra nếu voucher đã hết sau khi sử dụng
            if (voucher.getUsedCount() >= voucher.getQuantity()) {
                voucher.setStatus("used");
            }

           // orderRepository.save(order);
            voucherRepository.save(voucher);
        }
    }

    @Override
    public List<VoucherResponse> getVouchersByShopOwner(int shopOwnerId) {
        List<Voucher> vouchers = voucherRepository.findByShopOwner_Id(shopOwnerId);
        return vouchers.stream()
                .map(voucherMapper::toVoucherResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherResponse> getActiveVouchersByShop(int shopOwnerId) {
        List<Voucher> allVouchers = voucherRepository.findByShopOwner_Id(shopOwnerId);
        Date currentDate = new Date();

        // Lọc các voucher active và chưa hết hạn
        return allVouchers.stream()
                .filter(voucher ->
                        "active".equalsIgnoreCase(voucher.getStatus()) &&
                                voucher.getStartDate().before(currentDate) &&
                                voucher.getEndDate().after(currentDate) &&
                                voucher.getQuantity() > voucher.getUsedCount()
                )
                .map(voucherMapper::toVoucherResponse)
                .collect(Collectors.toList());
    }

}
