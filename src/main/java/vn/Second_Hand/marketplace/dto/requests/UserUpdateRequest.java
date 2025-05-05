package vn.Second_Hand.marketplace.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class UserUpdateRequest {
    @NotBlank(message = "FULL_NAME_INVALID")
    String fullName;

    @Pattern(regexp = "^\\d{10}$", message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    String gender;

    Date dateOfBirth;

}
