package vn.Second_Hand.marketplace.dto.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String fullName;
    String phoneNumber;
    String gender;
    Date dateOfBirth;
    String avt;
    String email;
    String username;
    Boolean isActive;
    String otp;
    LocalDateTime otpGenaratedTime;
    Set<String> roles;
}
