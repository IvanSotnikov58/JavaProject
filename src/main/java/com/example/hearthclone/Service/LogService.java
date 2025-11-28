package com.example.hearthclone.Service;
import com.example.hearthclone.Repository.LogRepository;
import com.example.hearthclone.model.LogEntry;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class LogService {
    private final LogRepository logRepository;
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }
    // zapisivaem deistvie v log
    public void addLog(Long matchId, String player, String action) {
        LogEntry entry = new LogEntry(matchId, player, action);
        logRepository.save(entry);
    }
    // poluchaem vse logi
    public List<LogEntry> getLogsByMatchId(Long matchId) {
        return logRepository.findByMatchIdOrderByTimestampAsc(matchId);
    }

    public void deleteLogsByMatchId(Long matchId) {
        List<LogEntry> logs = logRepository.findByMatchIdOrderByTimestampAsc(matchId);
        logRepository.deleteAll(logs);
    }
}