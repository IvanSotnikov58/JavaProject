package com.example.hearthclone.Service;

import com.example.hearthclone.model.Cards;
import com.example.hearthclone.model.Match;
import com.example.hearthclone.model.Turn;
import com.example.hearthclone.model.User;
import com.example.hearthclone.Repository.TurnRepository;
import com.example.hearthclone.Repository.CardRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TurnService {

    private final TurnRepository turnRepository;
    private final CardRepository cardRepository;

    public TurnService(TurnRepository turnRepository,
                       CardRepository cardRepository) {
        this.turnRepository = turnRepository;
        this.cardRepository = cardRepository;
    }

    public Turn playCard(Match match, User player, Cards card, String target, String result, int turnNumber) {
        Turn turn = new Turn();
        turn.setMatch(match);
        turn.setPlayer(player);
        turn.setCard(card);
        turn.setTarget(target);
        turn.setResult(result);
        turn.setTurnNumber(turnNumber);
        turn.setTimestamp(LocalDateTime.now());

        return turnRepository.save(turn);
    }

    public Cards getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElse(null);
    }
}
