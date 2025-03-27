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
    int productId;
    int ownerId;
    String productName;

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    Category category;

    String originalPrice;
    String currentPrice;
    int quantity;
    String origin;
    String warranty;
    String productCondition;
    String conditionDescription;
    String productDescription;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;
    int sold;
}
