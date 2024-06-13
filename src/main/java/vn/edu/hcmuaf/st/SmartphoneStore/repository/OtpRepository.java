package vn.edu.hcmuaf.st.SmartphoneStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.hcmuaf.st.SmartphoneStore.model.OTP;

public interface OtpRepository extends JpaRepository<OTP, Integer> {
    OTP findByOtp(String otp);
}
