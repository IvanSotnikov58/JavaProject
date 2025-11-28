package com.example.hearthclone.Controller;



import com.example.hearthclone.Service.CardService;
import com.example.hearthclone.model.Cards;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Получить все карты
    @GetMapping
    public List<Cards> getAllCards() {
        return cardService.getAllCards();
    }

    // Добавить новую карту
    @PostMapping
    public Cards addCard(@RequestBody Cards card) {
        return cardService.addCard(card);
    }

    // Удалить карту по id
    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    // Найти карты по имени
    @GetMapping("/name/{name}")
    public List<Cards> findByName(@PathVariable String name) {
        return cardService.findByName(name);
    }

    // Найти карты по типу
    @GetMapping("/type/{type}")
    public List<Cards> findByType(@PathVariable String type) {
        return cardService.findByType(type);
    }
}
