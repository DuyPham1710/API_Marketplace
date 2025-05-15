package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.VoucherResponse;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IVoucherService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/vouchers")
public class VoucherController {
    IVoucherService voucherService;

    // Lấy tất cả voucher của shop hiện tại
    @GetMapping("/my-shop")
    public ApiResponse<List<VoucherResponse>> getMyShopVouchers(@RequestParam int shopId) {
        List<VoucherResponse> vouchers = voucherService.getVouchersByShopOwner(shopId);

        return ApiResponse.<List<VoucherResponse>>builder()
                .message("Vouchers retrieved successfully")
                .data(vouchers)
                .build();
    }

    // Lấy các voucher có thể áp dụng của shop
    @GetMapping("/shop/{shopId}")
    public ApiResponse<List<VoucherResponse>> getShopActiveVouchers(@PathVariable int shopId) {
        List<VoucherResponse> vouchers = voucherService.getActiveVouchersByShop(shopId);

        return ApiResponse.<List<VoucherResponse>>builder()
                .message("Active vouchers retrieved successfully")
                .data(vouchers)
                .build();
    }

}
