package vn.Second_Hand.marketplace.service.Impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.Second_Hand.marketplace.dto.requests.LoginRequest;
import vn.Second_Hand.marketplace.dto.requests.RegisterRequest;
import vn.Second_Hand.marketplace.dto.requests.VerifyTokenRequest;
import vn.Second_Hand.marketplace.dto.responses.AuthResponse;
import vn.Second_Hand.marketplace.dto.responses.VerifyTokenResponse;
import vn.Second_Hand.marketplace.entity.User;
import vn.Second_Hand.marketplace.exception.AppException;
import vn.Second_Hand.marketplace.exception.ErrorCode;
import vn.Second_Hand.marketplace.mapper.UserMapper;
import vn.Second_Hand.marketplace.repository.UserRepository;
import vn.Second_Hand.marketplace.service.IAuthService;
import vn.Second_Hand.marketplace.service.IEmailService;
import vn.Second_Hand.marketplace.util.OtpUtil;
import vn.Second_Hand.marketplace.util.RoleUtil;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashSet;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements IAuthService {
    UserRepository userRepository;
    UserMapper userMapper;
    OtpUtil otpUtil;
    IEmailService emailService;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${jwt.signKey}")
    private String SIGN_KEY;

    public VerifyTokenResponse verifyToken(VerifyTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        Boolean verified = signedJWT.verify(verifier);
        return VerifyTokenResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }
    public String register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }

        String otp = otpUtil.generateOtp();
        try {
            emailService.sendOtpEmail(request.getEmail(), otp);
        }
        catch (MessagingException e) {
            throw new AppException(ErrorCode.UNABLE_SEND_OTP);
        }

        User user = userMapper.registerUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(RoleUtil.USER.name());

        user.setRoles(roles);
        user.setOtp(otp);
        user.setIsActive(false);
        user.setOtpGenaratedTime(LocalDateTime.now());

        userRepository.save(user);

        return "Registration successful, please check mail and verify account within 1 minute";
    }
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        Boolean authenticated =  passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        if (!user.getIsActive()) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVATED);
            //  return "Your account is not activated. Please verify your email to activate your account.";
        }
        String token = genarateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .token(token)
                .authenticated(true)
                .build();
    }

    public String genarateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer(user.getEmail())
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("scope", buildScope(user))
                .claim("userId", user.getId())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));
            return jwsObject.serialize();
        }catch (JOSEException e) {

            throw new RuntimeException(e);
        }
    }

    public String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
