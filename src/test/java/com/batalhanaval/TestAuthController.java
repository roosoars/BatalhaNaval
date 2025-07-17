package com.batalhanaval;

import com.batalhanaval.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

public class TestAuthController {
    
    private String registeredUserId = "test-user-id";
    private String registeredUserEmail = "test@example.com";
    private String registeredUserDisplayName = "Test User";

    public ResponseEntity<String> registerUser(User userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is required");
        }
        
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password is required");
        }
        
        if (userDTO.getUsername() == null || userDTO.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username is required");
        }
        
        return ResponseEntity.ok("Usuário registrado com ID: " + registeredUserId);
    }

    public ResponseEntity<String> loginUser(User userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email is required");
        }
        
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password is required");
        }
        
        if (registeredUserEmail.equals(userDTO.getEmail())) {
            return ResponseEntity.ok("Login bem-sucedido para o usuário: " + registeredUserDisplayName);
        }
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password");
    }
    
    public void setRegisteredUserId(String registeredUserId) {
        this.registeredUserId = registeredUserId;
    }
    
    public void setRegisteredUserEmail(String registeredUserEmail) {
        this.registeredUserEmail = registeredUserEmail;
    }
    
    public void setRegisteredUserDisplayName(String registeredUserDisplayName) {
        this.registeredUserDisplayName = registeredUserDisplayName;
    }
}