package com.example.hearthclone.Repository;

import com.example.hearthclone.model.Decks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Decks,Long> {
    List<Decks> findByPlayerId(Long PlayerId);
}
