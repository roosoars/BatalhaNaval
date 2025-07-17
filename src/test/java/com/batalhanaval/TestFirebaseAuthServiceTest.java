package com.batalhanaval;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Disabled("Incompatibilidade Mockito/ByteBuddy com JDK 24")
public class TestFirebaseAuthServiceTest {

    private final TestFirebaseAuthService authService = new TestFirebaseAuthService();

    @Test
    void testCreateUserReturnsUid() throws FirebaseAuthException {
        UserRecord.CreateRequest req = new UserRecord.CreateRequest()
                .setEmail("foo@bar.com")
                .setPassword("passwd123");
        authService.setRegisteredUserId("abc123");

        UserRecord ur = authService.createUser(req);

        assertNotNull(ur);
        assertEquals("abc123", ur.getUid());
    }

    @Test
    void testGetUserByEmailValid() throws FirebaseAuthException {
        authService.setRegisteredUserEmail("foo@bar.com");
        authService.setRegisteredUserDisplayName("Foo Bar");

        UserRecord ur = authService.getUserByEmail("foo@bar.com");

        assertNotNull(ur);
        assertEquals("Foo Bar", ur.getDisplayName());
    }

    @Test
    void testGetUserByEmailInvalid() throws FirebaseAuthException {
        UserRecord ur = authService.getUserByEmail("other@baz.com");
        assertNull(ur, "Emails não cadastrados devem retornar null");
    }
}
