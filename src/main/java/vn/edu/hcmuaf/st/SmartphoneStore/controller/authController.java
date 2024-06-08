package vn.edu.hcmuaf.st.SmartphoneStore.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.AuthenticationRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.RequestRegister;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.AuthenticationResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5000"})
@RequiredArgsConstructor
public class authController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RequestRegister request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
