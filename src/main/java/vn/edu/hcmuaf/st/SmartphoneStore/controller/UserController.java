package vn.edu.hcmuaf.st.SmartphoneStore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.st.SmartphoneStore.dto.UserDTO;
import vn.edu.hcmuaf.st.SmartphoneStore.model.User;
import vn.edu.hcmuaf.st.SmartphoneStore.service.impl.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    private final UserServiceImpl userServiceImpl;

    // Get all users
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<User> userList = userServiceImpl.findAll();
            List<UserDTO> result = new ArrayList<>();
            for(User user : userList){
                UserDTO userDTO = new UserDTO();
                userDTO.setId(user.getUserId());
                userDTO.setRole(user.getRole());
                userDTO.setPhoneNumber(user.getPhoneNumber());
                userDTO.setPassword(user.getPassword());
                userDTO.setEmail(user.getEmail());
                userDTO.setAddress(user.getAddress());
                userDTO.setUsername(user.getUsername());
                userDTO.setFullName(user.getFullName());
                result.add(userDTO);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getInfoUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO result = userServiceImpl.getInfoUser(user);
        return ResponseEntity.ok(result);
    }
    // Get a single user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
        User user = userServiceImpl.findById(id);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getUserId());
        userDTO.setRole(user.getRole());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setPassword(user.getPassword());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create a new user
    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        try {
            User result = userServiceImpl.createUser(user.convertToUser());
            result.setCreatedAt(LocalDateTime.now());
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable int id, @RequestBody UserDTO user) {
        User userToUpdate = userServiceImpl.findById(id);
        if (userToUpdate == null) {
            return ResponseEntity.notFound().build();
        }else {
            User userTmp = user.convertToUser();
            userTmp.setCreatedAt(userToUpdate.getCreatedAt());
            User result = userServiceImpl.updateUser(id, userTmp);
            return ResponseEntity.ok(result);
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        User user = userServiceImpl.findById(id);
        if (user != null) {
            userServiceImpl.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get a user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userServiceImpl.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Check if a user exists by ID
    @GetMapping("/exist/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable int id) {
        return ResponseEntity.ok(userServiceImpl.existById(id));
    }

    // Check if a user exists by username
    @GetMapping("/exist/username/{username}")
    public ResponseEntity<Boolean> existByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userServiceImpl.existByUsername(username));
    }
}
