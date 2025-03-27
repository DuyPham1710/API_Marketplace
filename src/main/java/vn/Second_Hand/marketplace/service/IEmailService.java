package vn.Second_Hand.marketplace.service;

import jakarta.mail.MessagingException;

public interface IEmailService {
    public void sendOtpEmail(String email, String otp) throws MessagingException;
}
