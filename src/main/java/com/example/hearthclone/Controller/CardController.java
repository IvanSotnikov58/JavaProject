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

    // poluchaaem karti
    @GetMapping
    public List<Cards> getAllCards() {
        return cardService.getAllCards();
    }

    // dobavljaem karti
    @PostMapping
    public Cards addCard(@RequestBody Cards card) {
        return cardService.addCard(card);
    }


    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
    }

    // nahodim karti po imeni
    @GetMapping("/name/{name}")
    public List<Cards> findByName(@PathVariable String name) {
        return cardService.findByName(name);
    }

    // nahodim karti po tipu
    @GetMapping("/type/{type}")
    public List<Cards> findByType(@PathVariable String type) {
        return cardService.findByType(type);
    }
}
