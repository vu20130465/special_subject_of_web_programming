package vn.edu.hcmuaf.st.SmartphoneStore.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.AuthenticationRequest;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.request.RequestRegister;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.response.AuthenticationResponse;
import vn.edu.hcmuaf.st.SmartphoneStore.model.Role;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.repository.UserRepository;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final jwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RequestRegister request) {
        var user = User.builder().username(request.getUsername()).password(passwordEncoder.encode(request.getPassword())).email(request.getEmail())
                .fullName(request.getFullName()).phoneNumber(request.getPhoneNumber()).address(request.getAddress()).role(Role.USER).build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
