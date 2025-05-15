package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.Voucher;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    Optional<Voucher> findByCode(String code);

    // Lấy danh sách voucher theo shop owner
    List<Voucher> findByShopOwner_Id(int shopOwnerId);

    // Lấy voucher hợp lệ theo mã và shop owner
    @Query("SELECT v FROM Voucher v WHERE v.code = :code AND v.shopOwner.Id = :shopOwnerId")
    Optional<Voucher> findByCodeAndShopOwnerId(@Param("code") String code, @Param("shopOwnerId") int shopOwnerId);

    // Kiểm tra mã voucher đã tồn tại chưa
    boolean existsByCode(String code);

}
