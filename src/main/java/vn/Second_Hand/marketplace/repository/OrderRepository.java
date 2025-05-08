package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.Order;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByBuyerOrderByCreatedAtDesc(User buyer);
    List<Order> findByBuyerAndStatusOrderByCreatedAtDesc(User buyer, String status);

}