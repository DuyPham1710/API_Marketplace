package vn.Second_Hand.marketplace.controller;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.*;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.AuthResponse;
import vn.Second_Hand.marketplace.dto.responses.VerifyTokenResponse;
import vn.Second_Hand.marketplace.service.IAuthService;
import vn.Second_Hand.marketplace.service.IUserService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    IAuthService authService;
    IUserService userService;

    @PostMapping("/register")
    public ApiResponse<?> register(@RequestBody RegisterRequest request) {
        return ApiResponse.builder()
                .message(authService.register(request))
                .build();
    }

    @PutMapping("/verify-account")
    public ApiResponse<?> verifyAccount(@RequestBody VerifyAccountRequest request) {
        return ApiResponse.builder()
                .message(userService.verifyAccount(request))
                .build();
    }

    @PutMapping("/regenerate-otp")
    public ApiResponse<?> regenerateOtp(@RequestBody EmailRequest request) {
        return ApiResponse.builder()
                .message(userService.regenerateOtp(request.getEmail()))
                .build();
      //  return new ResponseEntity<>(userService.regenerateOtp(request.getEmail()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest request) {
        var result = authService.login(request);
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .build();
      //  return new ResponseEntity<>(userService.login(request), HttpStatus.OK);
    }

    @PutMapping("/forgot-password")
    public ApiResponse<?> forgotPassword(@RequestBody EmailRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .message(userService.regenerateOtp(request.getEmail()))
                .build();
      //  return new ResponseEntity<>(userService.regenerateOtp(request.getEmail()), HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ApiResponse<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        return ApiResponse.<AuthResponse>builder()
                .message(userService.resetPassword(request))
                .build();
       // return new ResponseEntity<>(userService.resetPassword(request), HttpStatus.OK);
    }

    @PostMapping("/verify-token")
    ApiResponse<VerifyTokenResponse> verifyToken(@RequestBody VerifyTokenRequest request) throws ParseException, JOSEException {
        var result = authService.verifyToken(request);
        return ApiResponse.<VerifyTokenResponse>builder()
                .data(result)
                .build();
    }
}
