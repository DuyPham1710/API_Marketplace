package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.responses.VoucherResponse;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.entity.Voucher;

import java.util.List;

public interface IVoucherService {
    Voucher findByCode(String code);
    boolean isVoucherValid(Voucher voucher, double orderAmount);
    double calculateDiscount(Voucher voucher, double orderAmount);
    void applyVoucherToOrder(Order order, String voucherCode);

    Voucher findById(int voucherId);
    List<VoucherResponse> getVouchersByShopOwner(int shopOwnerId);
  //  VoucherResponse createVoucher(VoucherRequest request, int shopOwnerId);
  //  VoucherResponse updateVoucher(int voucherId, VoucherRequest request);
   // void deleteVoucher(int voucherId);
    List<VoucherResponse> getActiveVouchersByShop(int shopOwnerId);

}
