package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.UserDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.ChangePasswordRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements vn.edu.hcmuaf.st.SmartphoneStore.service.UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserDTO getInfoUser(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setAddress(user.getAddress());
        userDTO.setRole(user.getRole());
        userDTO.setId(user.getUserId());
       return userDTO;
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(int id, User user) {
        if (userRepository.existsById(id)) {
            User old = userRepository.findByUserId(id).orElseThrow(() -> new RuntimeException("user not found"));
            user.setUserId(id);
            user.setPassword(old.getPassword());
            return userRepository.save(user);
        } else {
            return null;
        }
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public boolean existById(int id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = userRepository.findByEmail(changePasswordRequest.getEmail()).orElseThrow(() -> new RuntimeException("user not found"));
        if (user != null && passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }
}
