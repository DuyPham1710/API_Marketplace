package vn.Second_Hand.marketplace.service;

import vn.Second_Hand.marketplace.dto.requests.*;
import vn.Second_Hand.marketplace.dto.responses.UserResponse;
import vn.Second_Hand.marketplace.entity.User;

import java.util.List;

public interface IUserService {
    public String verifyAccount(VerifyAccountRequest request);
    public String regenerateOtp(String email);
    public String resetPassword(ResetPasswordRequest request);
    public List<UserResponse> getAllUser();
    UserResponse updateUser(int userId, UserUpdateRequest updatedUser);
}
