package com.example.hearthclone.Repository;

import com.example.hearthclone.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurnRepository extends JpaRepository<Turn, Long> {

    List<Turn> findByMatch_IdOrderByTimestampAsc(Long matchId);

}