package battleship.service;

import battleship.dto.HistoryRequest;
import java.util.List;

public interface HistoryService {
    void addHistory(String uid, HistoryRequest dto);
    List<HistoryRequest> getHistory(String uid);
}
