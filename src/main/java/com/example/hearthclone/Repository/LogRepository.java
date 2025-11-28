package com.example.hearthclone.Repository;


import com.example.hearthclone.model.LogEntry;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByMatchId(Long matchId);
    List<LogEntry> findByTurnId(Long turnId);
    List<LogEntry> findByMatchIdOrderByTimestampAsc(Long MatchId);
}




