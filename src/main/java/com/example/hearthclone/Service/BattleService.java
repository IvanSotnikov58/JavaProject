package com.example.hearthclone.Service;

import com.example.hearthclone.Repository.*;
import com.example.hearthclone.model.*;
import org.springframework.stereotype.Service;
import com.example.hearthclone.model.Decks;


@Service
public class BattleService {
    private final MatchRepository matchRepository;
    private final TurnRepository turnRepository;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final LogRepository logRepository;

    // Razdacha kolod
    public BattleService(MatchRepository matchRepository, TurnRepository turnRepository, DeckRepository deckRepository, CardRepository cardRepository, LogRepository logRepository) {
        this.matchRepository = matchRepository;
        this.turnRepository = turnRepository;
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.logRepository = logRepository;
    }
    //Sdelat hod
    public Match MatchStarts(User player1, User player2) {
        Match match = new Match(player1, player2);
        matchRepository.save(match);
        Decks decks1= (Decks) deckRepository.findByPlayerId(player1.getId());
        Decks decks2= (Decks) deckRepository.findByPlayerId(player2.getId());
        match.setDeck1(decks1);
        match.setDeck2(decks2);
        matchRepository.save(match);
        return match;
    }
    public Turn PlayTurn(Long matchId, Long playerid, Long targetId) {
        Match match = matchRepository.findById(matchId).orElseThrow(()->
                new RuntimeException("Match ne naiden"));
        return null;
    }
}


