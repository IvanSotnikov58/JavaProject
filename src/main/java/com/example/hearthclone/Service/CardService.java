package com.example.hearthclone.Service;


import com.example.hearthclone.model.Cards;
import com.example.hearthclone.Repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    public List<Cards> getAllCards() {
        return cardRepository.findAll();
    }


    public Cards addCard(Cards card) {
        return cardRepository.save(card);
    }


    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }


    public List<Cards> findByName(String name) {
        return cardRepository.findByName(name);
    }


    public List<Cards> findByType(String type) {
        return cardRepository.findByType(type);
    }
}
