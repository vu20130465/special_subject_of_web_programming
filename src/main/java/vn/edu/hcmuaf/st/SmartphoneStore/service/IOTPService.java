package vn.edu.hcmuaf.st.SmartphoneStore.service;

import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

import java.util.Random;

public interface IOTPService {
    public  String generateRandomOTP();
    public void initOTP(String randomOTP, User user);
}
