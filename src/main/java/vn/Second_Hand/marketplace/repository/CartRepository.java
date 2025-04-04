package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.Second_Hand.marketplace.entity.Cart;
import vn.Second_Hand.marketplace.entity.Category;
import vn.Second_Hand.marketplace.entity.Product;
import vn.Second_Hand.marketplace.entity.User;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Optional<Cart> findByBuyerAndProduct(User buyer, Product product);
    boolean existsByBuyerAndProduct(User buyer, Product product);
}
