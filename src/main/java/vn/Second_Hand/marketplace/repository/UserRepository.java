package vn.Second_Hand.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.Second_Hand.marketplace.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String email);
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String email);
}
