package vn.Second_Hand.marketplace.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String fullName;
    String phoneNumber;
    String gender;
    Date dateOfBirth;
//    @Lob
//    private byte[] avt;
    String email;
    String username;
    String password;
    Boolean isActive;
    String otp;
    LocalDateTime otpGenaratedTime;
    Set<String> roles;
}
