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

    // poluchaem vse kolodi igroka
    @GetMapping("/player/{playerId}")
    public List<Decks> getDecks(@PathVariable Long playerId) {
        return deckService.getDecksByPlayer(playerId);
    }

    // sozdaem kolodu
    @PostMapping("/player/{playerId}")
    public Decks createDeck(@PathVariable Long playerId, @RequestBody DeckRequest request) {
        return deckService.createDeck(playerId, request.getCardIds());
    }



    @DeleteMapping("/{deckId}")
    public void deleteDeck(@PathVariable Long deckId) {
        deckService.deleteDeck(deckId);
    }
}
