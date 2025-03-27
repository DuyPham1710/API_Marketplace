package vn.Second_Hand.marketplace.service;

import com.nimbusds.jose.JOSEException;
import vn.Second_Hand.marketplace.dto.requests.LoginRequest;
import vn.Second_Hand.marketplace.dto.requests.RegisterRequest;
import vn.Second_Hand.marketplace.dto.requests.VerifyTokenRequest;
import vn.Second_Hand.marketplace.dto.responses.AuthResponse;
import vn.Second_Hand.marketplace.dto.responses.VerifyTokenResponse;
import vn.Second_Hand.marketplace.entity.User;

import java.text.ParseException;

public interface IAuthService {
    public String register(RegisterRequest request);
    public AuthResponse login(LoginRequest request);
    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws JOSEException, ParseException;
   // public AuthResponse Authentication(AuthRequest request);
    public String genarateToken(User user);
    public String buildScope(User user);

}
