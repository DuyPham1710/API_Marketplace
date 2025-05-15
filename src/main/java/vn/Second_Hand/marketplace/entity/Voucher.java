package vn.Second_Hand.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int voucherId;

    @ManyToOne
    @JoinColumn(name = "shop_owner_id")
    User shopOwner;

    String code;
    String description;

    // Loại voucher: percentage (%) hoặc fixed amount
    String discountType;

    // Giá trị giảm giá (% hoặc số tiền cố định)
    Double discountValue;

    // Số tiền tối thiểu của đơn hàng để áp dụng voucher
    Double minimumOrderAmount;

    // Số tiền giảm tối đa (cho voucher %)
    Double maximumDiscountAmount;

    Date startDate;
    Date endDate;

    // Trạng thái: active, expired, used
    String status;

    // Số lượng voucher có sẵn
    Integer quantity;

    // Số lượng đã sử dụng
    Integer usedCount;

    // Mối quan hệ one-to-many với Order
    @OneToMany(mappedBy = "voucher")
    List<Order> orders;

}
