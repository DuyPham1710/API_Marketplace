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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    User buyer;
    Date createdAt;
    String totalAmount;
    String address;
    String status;
    String paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails;
}
