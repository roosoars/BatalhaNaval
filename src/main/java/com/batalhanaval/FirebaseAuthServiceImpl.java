package com.batalhanaval;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;


@Service
public class FirebaseAuthServiceImpl implements FirebaseAuthService {
    
    private final FirebaseAuth firebaseAuth;
    
    public FirebaseAuthServiceImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }
    
    @Override
    public UserRecord createUser(UserRecord.CreateRequest request) throws FirebaseAuthException {
        return firebaseAuth.createUser(request);
    }
    
    @Override
    public UserRecord getUserByEmail(String email) throws FirebaseAuthException {
        return firebaseAuth.getUserByEmail(email);
    }
}