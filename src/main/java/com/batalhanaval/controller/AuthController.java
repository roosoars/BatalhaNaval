package com.batalhanaval.controller;

import com.batalhanaval.FirebaseAuthService;
import com.batalhanaval.model.User;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final FirebaseAuthService firebaseAuthService;

    public AuthController(FirebaseAuthService firebaseAuthService) {
        this.firebaseAuthService = firebaseAuthService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User userDTO) {
        try {
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(userDTO.getEmail())
                    .setPassword(userDTO.getPassword())
                    .setDisplayName(userDTO.getUsername());

            UserRecord userRecord = firebaseAuthService.createUser(request);
            return ResponseEntity.ok("Usuário registrado com ID: " + userRecord.getUid());
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro no registro: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User userDTO) {
        try {
            UserRecord u = firebaseAuthService.getUserByEmail(userDTO.getEmail());
            Map<String, String> resp = new HashMap<>();
            resp.put("userId", u.getUid());
            resp.put("username", u.getDisplayName());
            return ResponseEntity.ok(resp);
        } catch (FirebaseAuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
