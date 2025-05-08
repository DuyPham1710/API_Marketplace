package vn.Second_Hand.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    User buyer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @OneToOne
    @JoinColumn(name = "order_detail_id", nullable = false)
    OrderDetail orderDetail;

    @Column(name = "comment", columnDefinition = "TEXT")
    String comment;

    int stars;

    @Temporal(TemporalType.TIMESTAMP)
    Date reviewDate;

    @Column(name = "status", length = 50)
    String status;
}
