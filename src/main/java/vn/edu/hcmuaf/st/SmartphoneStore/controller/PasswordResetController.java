package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.OtpResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.ResetPasswordResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.OtpRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.EmailServiceImpl;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.OTPServiceImpl;
import vn.edu.hcmuaf.st.SmartphoneStore.model.OTP;
import java.util.Date;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api")
public class PasswordResetController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailServiceImpl emailService;
    @Autowired
    private OTPServiceImpl otpService;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/reset-password")
    public ResponseEntity<OtpResponse> resetPassword(@RequestParam("email") String email) {
        try {
            User user = userRepository.findByEmail(email).orElseThrow();

            String randomOTP = otpService.generateRandomOTP();
            otpService.initOTP(randomOTP,user);
            emailService.sendOtp(email,randomOTP);
            return ResponseEntity.ok(OtpResponse.builder().otp(randomOTP).message("Gửi mã OTP thành công").status(HttpStatus.OK.value()).build());
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(OtpResponse.builder().message("Không tìm thấy email").status(HttpStatus.BAD_REQUEST.value()).build());
        }
//        NoSuchElementException
    }
    @PostMapping("/reset-password/confirm")
    public ResponseEntity<ResetPasswordResponse> confirmResetPassword(@RequestParam("otp") String otpCode,
                                                  @RequestParam("password") String password) {
        OTP otp = otpRepository.findByOtp(otpCode);
        if(otp == null || otp.getExpiryDate().before(new Date())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResetPasswordResponse.builder().message("OTP không hợp lệ hoặc đã hết hạn").status(HttpStatus.BAD_REQUEST.value()).build());
        }
        User user = otp.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        otpRepository.delete(otp);
        return ResponseEntity.ok(ResetPasswordResponse.builder().message("Mật khẩu được đặt lại thành công").status(HttpStatus.OK.value()).build());
    }
}
