package vn.Second_Hand.marketplace.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.Second_Hand.marketplace.dto.responses.VoucherResponse;
import vn.Second_Hand.marketplace.entity.Voucher;

@Component
public class VoucherMapper {
    @Autowired
    UserMapper userMapper;

    public VoucherResponse toVoucherResponse(Voucher voucher) {
        return VoucherResponse.builder()
                .voucherId(voucher.getVoucherId())
                .code(voucher.getCode())
                .description(voucher.getDescription())
                .discountType(voucher.getDiscountType())
                .discountValue(voucher.getDiscountValue())
                .minimumOrderAmount(voucher.getMinimumOrderAmount())
                .maximumDiscountAmount(voucher.getMaximumDiscountAmount())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .status(voucher.getStatus())
                .quantity(voucher.getQuantity())
                .usedCount(voucher.getUsedCount())
                .shopOwner(userMapper.toUserResponse(voucher.getShopOwner()))
                .build();
    }

}
