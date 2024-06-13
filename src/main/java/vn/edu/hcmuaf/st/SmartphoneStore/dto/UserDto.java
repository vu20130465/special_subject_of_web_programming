package vn.edu.hcmuaf.st.SmartphoneStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Role;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;

    public User convertToUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.setFullName(this.fullName);
        user.setPhoneNumber(this.phoneNumber);
        user.setAddress(this.address);
        user.setRole(Role.USER);
        return user;
    }
}
