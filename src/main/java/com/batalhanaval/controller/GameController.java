package com.batalhanaval.controller;

import com.batalhanaval.model.Game;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private Firestore firestore;

    // Handle root path requests
    @PostMapping("/")
    public String createGameRoot(@RequestParam String player1Id) throws Exception {
        return createGame(player1Id);
    }

    // 1) Cria partida (sem player2 ainda)
    @PostMapping("/create")
    public String createGame(@RequestParam String player1Id) throws Exception {
        System.out.println("[DEBUG] createGame chamado com player1Id=" + player1Id);
        String gameId = UUID.randomUUID().toString();
        Game g = new Game();
        g.setGameId(gameId);
        g.setPlayer1Id(player1Id);
        g.setCreatedAt(new Date());

        try {
            ApiFuture<WriteResult> f = firestore.collection("Games")
                    .document(gameId)
                    .set(g);
            WriteResult wr = f.get();
            System.out.println("[DEBUG] Game salvo em Firestore em: " + wr.getUpdateTime());
        } catch (Exception e) {
            System.err.println("[ERROR] falha ao salvar Game: " + e.getMessage());
            e.printStackTrace();
        }
        return gameId;
    }

    // 2) Lista partidas abertas (sem player2)
    @GetMapping("/open")
    public List<Game> listOpenGames() throws Exception {
        ApiFuture<QuerySnapshot> q = firestore.collection("Games")
                .whereEqualTo("player2Id", null)
                .get();
        List<Game> out = new ArrayList<>();
        for (DocumentSnapshot doc : q.get().getDocuments()) {
            out.add(doc.toObject(Game.class));
        }
        return out;
    }

    // 3) Player2 entra na partida
    @PostMapping("/join")
    public String joinGame(@RequestParam String gameId, @RequestParam String player2Id) throws Exception {
        DocumentReference ref = firestore.collection("Games").document(gameId);
        Map<String, Object> updates = new HashMap<>();
        updates.put("player2Id", player2Id);
        ref.update(updates).get();
        return "joined";
    }

    // 4) Busca estado de uma partida
    @GetMapping("/{gameId}")
    public Game getGame(@PathVariable String gameId) throws Exception {
        DocumentSnapshot doc = firestore.collection("Games")
                .document(gameId)
                .get().get();
        return doc.toObject(Game.class);
    }
}
