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
    ACCOUNT_NOT_ACTIVATED(1009, "Your account is not activated. Please verify your email to activate your account"),
    USER_EXISTED(1010, "User already exists"),
    INVALID_MESSAGE(1111, "Invalid message"),
    PRODUCT_NOT_FOUND(1112, "Product not found"),
    CART_NOT_FOUND(1113, "Cart item not found"),
    NOT_MATCH_PASSWORD(1114, "Password and confirm password do not match. Please try again!"),
    FULL_NAME_INVALID(1115, "Full name is required"),
    PHONE_NUMBER_INVALID(1116, "Phone number must be 10 digits"),
    ORDER_NOT_FOUND(1117, "Order not found"),
    REVIEW_NOT_FOUND(1118, "Review not found"),
    UNAUTHORIZED(1119, "Unauthorized"),
    NOT_ORDER_OWNER(1120, "You are not the owner of this order"),
    ORDER_CANNOT_BE_CANCELLED(1121, "Order cannot be cancelled"),
    ADDRESS_NOT_FOUND(1122, "Address not found"),
    PRODUCT_OUT_OF_STOCK(1123, "Product is out of stock"),
    VOUCHER_NOT_FOUND(1124, "Voucher not found"),


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
