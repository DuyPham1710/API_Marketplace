package vn.Second_Hand.marketplace.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.util.RoleUtil;

import java.util.HashSet;

@Configuration
public class ApplicationConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                var roles = new HashSet<String>();
                roles.add(RoleUtil.ADMIN.name());

                User user = User.builder()
                        .username("admin")
                        .email("admin@gmail.com")
                        .password(passwordEncoder.encode("admin"))
                        .isActive(true)
                        .roles(roles)
                        .build();
                userRepository.save(user);
            }
        };
    }
}
