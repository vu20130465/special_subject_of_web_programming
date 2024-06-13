package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.model.OTP;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.OtpRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.service.IOTPService;

import java.util.Date;
import java.util.Random;

@Service
public class OTPServiceImpl implements IOTPService {
    @Autowired
    private OtpRepository otpRepository;
    private static final int LENGTH = 6;
    public  String generateRandomOTP() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            int digit = random.nextInt(10); // Chọn số ngẫu nhiên từ 0 đến 9
            sb.append(digit);
        }
        return sb.toString();
    }

    @Override
    public void initOTP(String randomOTP, User user) {
        OTP otp = new OTP();
        otp.setOtp(randomOTP);
        otp.setUser(user);
        otp.setExpiryDate(new Date(System.currentTimeMillis() + 3600000 ));
        otpRepository.save(otp);
    }
}
