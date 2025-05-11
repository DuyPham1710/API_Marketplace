package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.DeliveryAddress;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
    List<DeliveryAddress> findByBuyer(User buyer);

    @Modifying
    @Query("UPDATE DeliveryAddress da SET da.defaultAddress = 0 WHERE da.buyer.id = :buyerId")
    void updateAllDefaultFalse(@Param("buyerId") int buyerId);

    @Modifying
    @Query("UPDATE DeliveryAddress da SET da.defaultAddress = 1 WHERE da.addressId = :addressId")
    void setDefaultAddress(@Param("addressId") Long addressId);
}

