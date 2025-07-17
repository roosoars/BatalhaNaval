package com.batalhanaval.controller;

import com.batalhanaval.model.Game;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import com.google.firebase.cloud.FirestoreClient;
import com.google.cloud.firestore.Firestore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QuerySnapshot;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @GetMapping("/get")
    public List<Game> getGameHistory(@RequestParam String userId) {
        try {
            Firestore db = FirestoreClient.getFirestore();

            ApiFuture<QuerySnapshot> player1Future = db.collection("games")
                    .whereEqualTo("player1Id", userId)
                    .get();

            ApiFuture<QuerySnapshot> player2Future = db.collection("games")
                    .whereEqualTo("player2Id", userId)
                    .get();

            List<Game> games = new ArrayList<>();

            games.addAll(player1Future.get().toObjects(Game.class));

            games.addAll(player2Future.get().toObjects(Game.class));

            return games;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving game history: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
