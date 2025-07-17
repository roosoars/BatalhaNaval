package com.batalhanaval;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.mockito.Mockito;


public class TestFirebaseAuthService implements FirebaseAuthService {

    private String registeredUserId = "test-user-id";
    private String registeredUserEmail = "test@example.com";
    private String registeredUserDisplayName = "Test User";

    @Override
    public UserRecord createUser(UserRecord.CreateRequest request) throws FirebaseAuthException {
        UserRecord mockUserRecord = Mockito.mock(UserRecord.class, Mockito.RETURNS_DEEP_STUBS);
        Mockito.when(mockUserRecord.getUid()).thenReturn(registeredUserId);
        return mockUserRecord;
    }

    @Override
    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        if (registeredUserEmail.equals(email)) {
            UserRecord mockUserRecord = Mockito.mock(UserRecord.class, Mockito.RETURNS_DEEP_STUBS);
            Mockito.when(mockUserRecord.getDisplayName()).thenReturn(registeredUserDisplayName);
            return mockUserRecord;
        }

        return null;
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
