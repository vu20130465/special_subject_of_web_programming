package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

public interface OTPService {
    public  String generateRandomOTP();
    public void initOTP(String randomOTP, User user);
}
