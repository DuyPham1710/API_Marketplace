package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.LoginRequest;
import vn.Second_Hand.marketplace.dto.requests.RegisterRequest;
import vn.Second_Hand.marketplace.dto.requests.ResetPasswordRequest;
import vn.Second_Hand.marketplace.dto.requests.VerifyAccountRequest;
import vn.Second_Hand.marketplace.dto.responses.UserResponse;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

public interface IUserService {
    public String verifyAccount(VerifyAccountRequest request);
    public String regenerateOtp(String email);
    public String resetPassword(ResetPasswordRequest request);
    public List<UserResponse> getAllUser();

}
