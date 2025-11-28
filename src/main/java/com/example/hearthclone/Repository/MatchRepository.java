package com.example.hearthclone.Repository;

import com.example.hearthclone.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByPlayer01_Id(Long playerId);
    List<Match> findByPlayer02_Id(Long playerId);
    List<Match> findByWinner_Id(Long winnerId);
    List<Match> findByStatus(String status);
}
