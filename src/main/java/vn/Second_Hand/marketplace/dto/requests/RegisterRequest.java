package vn.Second_Hand.marketplace.dto.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    String fullName;
    String phoneNumber;
    String gender;
    Date dateOfBirth;
    String email;
    String username;
    String password;
}
