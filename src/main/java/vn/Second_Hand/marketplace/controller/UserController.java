package vn.Second_Hand.marketplace.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import vn.Second_Hand.marketplace.dto.requests.*;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.UserResponse;
import vn.Second_Hand.marketplace.service.IUserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequestMapping("/users")
public class UserController {
    IUserService userService;

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUser() {
//        var auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Username: {}", auth.getName());
//        auth.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .message("Categories")
                .data(userService.getAllUser())
                .build();
    }
    @PutMapping("/{userid}")
    public ApiResponse<UserResponse> updateUser(@PathVariable int userid, @RequestBody UserUpdateRequest req) {
        return ApiResponse.<UserResponse>builder()
                .message("updated successfully!")
                .data(userService.updateUser(userid, req))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .message("Get user successfully")
                .data(userService.getMyInfo())
                .build();
    }
}
