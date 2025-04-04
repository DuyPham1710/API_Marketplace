package vn.Second_Hand.marketplace.exception;

public enum ErrorCode {
    EMAIL_EXISTED(1001, "Email already exists"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategoried error"),
    USERNAME_INVALID(1002, "Username must be at least 8 characters"),
    PASSWORD_INVALID(1003, "Password must be at least 8 characters"),
    USER_NOT_EXISTED(1004, "User not existed"),
    UNAUTHENTICATED(1005, "Unauthenticated"),

    UNABLE_SEND_OTP(1006, "Unable send otp"),
    USER_NOT_FOUND(1007, "User not found"),
    OTP_INVALID_OR_EXPIRED(1008, "OTP is invalid or has expired. Please regenerate a new OTP and try again."),
    ACOUNT_NOT_ACTIVATED(1009, "Your account is not activated. Please verify your email to activate your account"),
    USER_EXISTED(1010, "User already exists"),
    INVALID_MESSAGE(1111, "Invalid message"),
    PRODUCT_NOT_FOUND(1112, "Product not found"),
    CART_NOT_FOUND(1113, "Cart item not found"),
    NOT_MATCH_PASSWORD(1114, "Password and confirm password do not match. Please try again!")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
