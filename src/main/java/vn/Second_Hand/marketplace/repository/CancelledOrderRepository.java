package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.CancelledOrder;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

@Repository
public interface CancelledOrderRepository extends JpaRepository<CancelledOrder, Integer> {
    List<CancelledOrder> findByBuyerOrderByCancelledAtDesc(User buyer);
    List<CancelledOrder> findByOrderOrderIdOrderByCancelledAtDesc(int orderId);
}
