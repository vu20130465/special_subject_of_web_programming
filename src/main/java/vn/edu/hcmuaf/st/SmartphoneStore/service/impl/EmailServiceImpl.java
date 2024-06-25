package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.service.EmailService;
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendOtp(String email, String otp) {
        String yourOtp = "This is your OTP:" + otp;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("OTP request");
        message.setText(yourOtp);
        mailSender.send(message);
    }
}
