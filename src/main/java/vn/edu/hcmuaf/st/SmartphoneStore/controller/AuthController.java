package vn.edu.hcmuaf.st.SmartphoneStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.AuthenticationRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.ChangePasswordRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.RequestRegister;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.AuthenticationResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.AuthenticationServiceImpl;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.UserServiceImpl;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationServiceImpl authenticationServiceImpl;
    private final UserServiceImpl userServiceImpl;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RequestRegister request) {
        return ResponseEntity.ok(authenticationServiceImpl.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationServiceImpl.authenticate(request));
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean isChanged = userServiceImpl.changePassword(changePasswordRequest);
        if (isChanged) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid old password.");
        }
    }
}
