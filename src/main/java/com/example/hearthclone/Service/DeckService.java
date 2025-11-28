package com.example.hearthclone.Service;

import com.example.hearthclone.model.Cards;
import com.example.hearthclone.model.Decks;
import com.example.hearthclone.model.User;
import com.example.hearthclone.Repository.DeckRepository;
import com.example.hearthclone.Repository.CardRepository;
import com.example.hearthclone.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;

    public DeckService(DeckRepository deckRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    // poluchaem
    public List<Decks> getDecksByPlayer(Long playerId) {
        return deckRepository.findByPlayerId(playerId);
    }


    public Decks createDeck(Long playerId, List<Long> cardIds) {
        Optional<User> userOpt = userRepository.findById(playerId);
        if (userOpt.isEmpty()) return null; // vkidivaem iskluichenie

        List<Cards> cards = cardRepository.findAllById(cardIds);
        Decks deck = new Decks(userOpt.get(), cards);
        deckRepository.save(deck);

        deck.setPlayer(userOpt.get());
        deck.setCards(cards);

        return deckRepository.save(deck);
    }

    public void deleteDeck(Long deckId) {
        deckRepository.deleteById(deckId);
    }
}
