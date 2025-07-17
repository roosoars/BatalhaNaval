package com.batalhanaval;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;


public interface FirebaseAuthService {
    
    /**
     * Creates a new user in Firebase Authentication.
     *
     * @param request the user creation request
     * @return the created user record
     * @throws FirebaseAuthException if an error occurs
     */
    UserRecord createUser(UserRecord.CreateRequest request) throws FirebaseAuthException;
    
    /**
     * Gets a user by email from Firebase Authentication.
     *
     * @param email the email of the user to get
     * @return the user record
     * @throws FirebaseAuthException if an error occurs
     */
    UserRecord getUserByEmail(String email) throws FirebaseAuthException;
}