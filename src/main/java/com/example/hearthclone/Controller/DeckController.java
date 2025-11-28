package com.example.hearthclone.Controller;

import com.example.hearthclone.Service.DeckService;
import com.example.hearthclone.model.Decks;
import org.springframework.web.bind.annotation.*;
import com.example.hearthclone.dto.DeckRequest;


import java.util.List;

@RestController
@RequestMapping("/decks")
public class DeckController {

    private final DeckService deckService;

    public DeckController(DeckService deckService) {
        this.deckService = deckService;
    }

    // Получить все колоды игрока
    @GetMapping("/player/{playerId}")
    public List<Decks> getDecks(@PathVariable Long playerId) {
        return deckService.getDecksByPlayer(playerId);
    }

    // Создать новую колоду
    @PostMapping("/player/{playerId}")
    public Decks createDeck(@PathVariable Long playerId, @RequestBody DeckRequest request) {
        return deckService.createDeck(playerId, request.getCardIds());
    }


    // Удалить колоду
    @DeleteMapping("/{deckId}")
    public void deleteDeck(@PathVariable Long deckId) {
        deckService.deleteDeck(deckId);
    }
}
