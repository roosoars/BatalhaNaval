package com.batalhanaval.controller;

import com.batalhanaval.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private Firestore firestore;

    @PostMapping("/create")
    public String createUser(@RequestParam String username) throws ExecutionException, InterruptedException {
        System.out.println("[DEBUG] createUser chamado com username=" + username);
        String userId = UUID.randomUUID().toString();
        User u = new User();
        u.setUserId(userId);
        u.setUsername(username);

        try {
            CollectionReference users = firestore.collection("Users");
            ApiFuture<WriteResult> f = users.document(userId).set(u);
            WriteResult wr = f.get();
            System.out.println("[DEBUG] User salvo em Firestore em: " + wr.getUpdateTime());
        } catch (Exception e) {
            System.err.println("[ERROR] falha ao salvar User: " + e.getMessage());
            e.printStackTrace();
        }
        return userId;
    }
}
