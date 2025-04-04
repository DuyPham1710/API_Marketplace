package vn.Second_Hand.marketplace.service.Impl;

import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.dto.requests.*;
import vn.Second_Hand.marketplace.dto.responses.ApiResponse;
import vn.Second_Hand.marketplace.dto.responses.UserResponse;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.UserMapper;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IEmailService;
import vn.Second_Hand.marketplace.service.IUserService;
import vn.Second_Hand.marketplace.util.OtpUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements IUserService {
    UserRepository userRepository;
    OtpUtil otpUtil;
    UserMapper userMapper;
    IEmailService emailService;
    PasswordEncoder passwordEncoder;


    public String verifyAccount(VerifyAccountRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (user.getOtp().equals(request.getOtp()) && Duration.between(user.getOtpGenaratedTime(), LocalDateTime.now()).getSeconds() < 60) {
            user.setIsActive(true);
            userRepository.save(user);
            return "OTP verified";
        }
        throw new AppException(ErrorCode.OTP_INVALID_OR_EXPIRED);
    }
    public String regenerateOtp(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String otp = otpUtil.generateOtp();

        try {
            emailService.sendOtpEmail(email, otp);
        }
        catch (MessagingException e) {
            throw new AppException(ErrorCode.UNABLE_SEND_OTP);
        }

        user.setOtp(otp);
        user.setOtpGenaratedTime(LocalDateTime.now());
        userRepository.save(user);
        return "A new OTP has been sent to your email. Please check your inbox and verify account within 1 minute.";
    }

    public String resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//
//        if (user.getOtp().equals(request.getOtp()) && Duration.between(user.getOtpGenaratedTime(), LocalDateTime.now()).getSeconds() < 60) {
        if (request.getNewPassword().equals(request.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));

            userRepository.save(user);
            return "Password has been successfully reset for email: " + request.getEmail();
        }
       // throw new AppException(ErrorCode.OTP_INVALID_OR_EXPIRED);
        throw new AppException(ErrorCode.NOT_MATCH_PASSWORD);
    }

    public List<UserResponse> getAllUser() {
        return userMapper.toUserResponse(userRepository.findAll());
    }

    public UserResponse updateUser(int userId, UserUpdateRequest updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(updatedUser, user);
        return userMapper.toUserResponse(userRepository.save(user));
    }
}
