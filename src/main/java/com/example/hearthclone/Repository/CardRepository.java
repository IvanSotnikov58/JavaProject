package com.example.hearthclone.Repository;

import com.example.hearthclone.model.Cards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Cards, Long> {
    List<Cards> findByName(String Name);
    List<Cards> findByType(String Type);


}
