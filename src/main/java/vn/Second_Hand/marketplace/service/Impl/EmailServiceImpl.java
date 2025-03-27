package vn.Second_Hand.marketplace.service.Impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.Second_Hand.marketplace.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("Verify OTP");

        String htmlContent = String.format("""
        <div style="font-family: Arial, sans-serif; line-height: 1.5; color: #333;">
            <h2 style="color: #555;">Verify Your Email</h2>
            <p>Thank you for registering with us. Please use the following OTP to complete your verification:</p>
            <p style="font-size: 18px; font-weight: bold; color: #000;">%s</p>
            <p>This OTP is valid for a limited time. Please do not share it with anyone.</p>
            <hr>
            <p style="font-size: 12px; color: #888;">If you did not request this, please ignore this email or contact our support team.</p>
        </div>
        """, otp);

        mimeMessageHelper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
