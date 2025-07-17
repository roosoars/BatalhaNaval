package com.batalhanaval;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import com.batalhanaval.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

class AuthControllerTest {

    private TestAuthController controller;

    @BeforeEach
    void setUp() {
        controller = new TestAuthController();
        controller.setRegisteredUserId("testUid");
        controller.setRegisteredUserEmail("test@example.com");
        controller.setRegisteredUserDisplayName("testUser");
    }

    @Test
    void testRegisterUser() {
        User dto = new User();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");
        dto.setUsername("testUser");

        ResponseEntity<String> resp = controller.registerUser(dto);
        assertEquals(OK, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Usuário registrado"));
        assertTrue(resp.getBody().contains("testUid"));
    }

    @Test
    void testRegisterUserWithMissingEmail() {
        User dto = new User();
        dto.setPassword("pw");
        dto.setUsername("name");

        ResponseEntity<String> resp = controller.registerUser(dto);
        assertEquals(BAD_REQUEST, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Email is required"));
    }

    @Test
    void testRegisterUserWithMissingPassword() {
        User dto = new User();
        dto.setEmail("a@b.com");
        dto.setUsername("name");

        ResponseEntity<String> resp = controller.registerUser(dto);
        assertEquals(BAD_REQUEST, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Password is required"));
    }

    @Test
    void testRegisterUserWithMissingUsername() {
        User dto = new User();
        dto.setEmail("a@b.com");
        dto.setPassword("pw");

        ResponseEntity<String> resp = controller.registerUser(dto);
        assertEquals(BAD_REQUEST, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Username is required"));
    }

    @Test
    void testLoginUser() {
        User dto = new User();
        dto.setEmail("test@example.com");
        dto.setPassword("password123");

        ResponseEntity<String> resp = controller.loginUser(dto);
        assertEquals(OK, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Login bem-sucedido"));
        assertTrue(resp.getBody().contains("testUser"));
    }

    @Test
    void testLoginUserWithMissingEmail() {
        User dto = new User();
        dto.setPassword("pw");
        ResponseEntity<String> resp = controller.loginUser(dto);
        assertEquals(BAD_REQUEST, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Email is required"));
    }

    @Test
    void testLoginUserWithMissingPassword() {
        User dto = new User();
        dto.setEmail("test@example.com");
        ResponseEntity<String> resp = controller.loginUser(dto);
        assertEquals(BAD_REQUEST, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Password is required"));
    }

    @Test
    void testLoginUserWithInvalidEmail() {
        User dto = new User();
        dto.setEmail("wrong@example.com");
        dto.setPassword("pw");
        ResponseEntity<String> resp = controller.loginUser(dto);
        assertEquals(UNAUTHORIZED, resp.getStatusCode());
        assertTrue(resp.getBody().contains("Invalid email or password"));
    }
}
