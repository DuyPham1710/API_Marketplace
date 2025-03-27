package vn.Second_Hand.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    Long imageId;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Column(name = "initial_image", nullable = false)
    String initialImage;

    @Column(name = "current_image", nullable = false)
    String currentImage;
}
