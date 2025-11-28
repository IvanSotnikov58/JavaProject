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

    // Получить все карты
    public List<Cards> getAllCards() {
        return cardRepository.findAll();
    }

    // Добавить карту
    public Cards addCard(Cards card) {
        return cardRepository.save(card);
    }

    // Удалить карту по id
    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    // Найти карту по имени
    public List<Cards> findByName(String name) {
        return cardRepository.findByName(name);
    }

    // Найти карты по типу
    public List<Cards> findByType(String type) {
        return cardRepository.findByType(type);
    }
}
