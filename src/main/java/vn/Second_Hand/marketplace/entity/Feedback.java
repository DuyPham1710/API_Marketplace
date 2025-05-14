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
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    int feedbackId;

    @ManyToOne
    @JoinColumn(name = "feedbacker_id", nullable = false)
    User feedbacker;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "comment", columnDefinition = "TEXT")
    String comment;

    @Column(name = "star", nullable = false)
    int star;

    @Column(name = "order_id", nullable = false)
    int orderId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
    Date createdAt;
}
