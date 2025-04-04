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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    int productId;

    @Column(name = "owner_id", nullable = false)
    int ownerId;

    @Column(name = "product_name", nullable = false, length = 255)
    String productName;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @Column(name = "original_price", nullable = false, length = 50)
    String originalPrice;

    @Column(name = "current_price", nullable = false, length = 50)
    String currentPrice;

    @Column(name = "quantity", nullable = false)
    int quantity;

    @Column(name = "origin", length = 255)
    String origin;

    @Column(name = "warranty", length = 100)
    String warranty;

    @Column(name = "product_condition", length = 50)
    String productCondition;

    @Column(name = "condition_description", columnDefinition = "TEXT")
    String conditionDescription;

    @Column(name = "product_description", columnDefinition = "TEXT")
    String productDescription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    Date createdAt;

    @Column(name = "sold", nullable = false)
    int sold;
}
