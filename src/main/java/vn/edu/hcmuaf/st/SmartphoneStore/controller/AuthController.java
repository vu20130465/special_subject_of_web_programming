package vn.edu.hcmuaf.st.SmartphoneStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.AuthenticationRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.ChangePasswordRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.RequestRegister;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.AuthenticationResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.AuthenticationService;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:3000"})
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RequestRegister request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        boolean isChanged = userService.changePassword(changePasswordRequest);
        if (isChanged) {
            return ResponseEntity.ok("Password changed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid old password.");
        }
    }
}
