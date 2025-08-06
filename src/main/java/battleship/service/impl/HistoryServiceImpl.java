package battleship.service.impl;

import battleship.service.HistoryService;
import battleship.dto.HistoryRequest;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryServiceImpl implements HistoryService {
    private final Firestore db = FirestoreClient.getFirestore();

    @Override
    public void addHistory(String uid, HistoryRequest dto) {
        db.collection("users")
                .document(uid)
                .collection("games")
                .add(dto);
    }

    @Override
    public List<HistoryRequest> getHistory(String uid) {
        try {
            ApiFuture<QuerySnapshot> future = db.collection("users")
                    .document(uid)
                    .collection("games")
                    .get();
            List<QueryDocumentSnapshot> docs = future.get().getDocuments();
            return docs.stream()
                    .map(doc -> doc.toObject(HistoryRequest.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
