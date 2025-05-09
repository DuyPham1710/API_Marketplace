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
@Table(name = "cancelled_orders")
public class CancelledOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int cancelId;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    User buyer;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Column(nullable = false)
    String reason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    Date cancelledAt;

    @PrePersist
    public void prePersist() {
        cancelledAt = new Date();
    }

}
