package vn.edu.hcmuaf.st.SmartphoneStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Role;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;
    public User convertToUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(passwordEncoder.encode(this.password));
        user.setEmail(this.email);
        user.setFullName(this.fullName);
        user.setPhoneNumber(this.phoneNumber);
        user.setAddress(this.address);
        user.setRole(this.role);
        return user;
    }
}
