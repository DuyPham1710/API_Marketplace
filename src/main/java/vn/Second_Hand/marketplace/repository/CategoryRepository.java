package vn.Second_Hand.marketplace.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
