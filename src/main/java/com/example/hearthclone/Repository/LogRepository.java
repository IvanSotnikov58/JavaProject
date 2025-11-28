package com.example.hearthclone.Repository;


import com.example.hearthclone.model.LogEntry;

import lombok.extern.java.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByMatchId(Long MatchId);
    List<LogEntry> findByTurnId(Long TurnId);
    List<LogEntry> findByMatchIdOrderByTimestampAsc(Long MatchId);
}




