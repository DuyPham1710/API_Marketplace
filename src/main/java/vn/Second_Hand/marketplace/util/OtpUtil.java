package vn.Second_Hand.marketplace.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class OtpUtil {
    public String generateOtp(){
        Random random = new Random();
        int otpInt = 100000 + random.nextInt(900000);
        String otpStr = Integer.toString(otpInt);

        return otpStr;
    }
}
