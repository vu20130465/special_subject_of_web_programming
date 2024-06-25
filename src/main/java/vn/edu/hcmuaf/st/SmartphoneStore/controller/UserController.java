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
import java.util.List;

@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class UserController {
    private final UserServiceImpl userServiceImpl;

    // Get all users
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> result = userServiceImpl.findAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
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
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = userServiceImpl.findById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
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
