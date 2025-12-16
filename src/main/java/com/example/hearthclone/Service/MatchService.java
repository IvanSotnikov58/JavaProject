package com.example.hearthclone.Service;

import com.example.hearthclone.Repository.TurnRepository;
import com.example.hearthclone.config.MatchWebSocketHandler;
import com.example.hearthclone.dto.MatchStateResponse;
import com.example.hearthclone.model.*;
import com.example.hearthclone.Repository.MatchRepository;
import com.example.hearthclone.Repository.DeckRepository;
import com.example.hearthclone.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final TurnService turnService;
    private final TurnRepository turnRepository;

    public MatchService(MatchRepository matchRepository,
                        DeckRepository deckRepository,
                        UserRepository userRepository,
                        TurnService turnService,
                        TurnRepository turnRepository) {
        this.matchRepository = matchRepository;
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.turnService = turnService;
        this.turnRepository = turnRepository;
    }

    // sozdaem match
    public Optional<Object> createMatch(Long player01Id, Long player02Id) {
        Optional<User> player01 = userRepository.findById(player01Id);
        Optional<User> player02 = userRepository.findById(player02Id);

        if (player01.isEmpty() || player02.isEmpty()) {
            return Optional.empty();
        }

        Match match = new Match(player01.get(), player02.get(), null, "ONGOING");
        match = matchRepository.save(match);

        // razdacha kolod
        dealCards(match, player01.get());
        dealCards(match, player02.get());

        return Optional.of(match);
    }

    @Autowired
    private MatchWebSocketHandler wsHandler;

    public void playTurn(Match match, User player, Cards card, String target, String result, int turnNumber) {
        Turn turn = turnService.playCard(match, player, card, target, result, turnNumber);

        // Обновляем статус матча
        if (turnNumber >= 10) {
            match.setStatus("FINISHED");
            matchRepository.save(match);
        }

        //sozdaem MatchStateResponse
        MatchStateResponse state = new MatchStateResponse(
                match.getId(),
                match.getPlayer01(),
                match.getPlayer02(),
                turnService.getTurns(match),
                match.getStatus()
        );

        // Отправляем всем подключённым клиентам
        wsHandler.broadcast(state);
    }

    private void dealCards(Match match, User player) {
        List<Decks> decks = deckRepository.findByPlayerId(player.getId());
        if (decks.isEmpty()) return;

        List<Cards> cards = decks.get(0).getCards();
        Collections.shuffle(cards);

        //Poka prosto vyvodim v konsol' ili mozhno sokhranit' v otdel'noye pole match (naprimer, map player->cards)
        System.out.println("Разданы карты для " + player.getName() + ": " + cards);
    }



    public Optional<Match> getMatch(Long matchId) {
        return matchRepository.findById(matchId);
    }

    public Cards getCardFromService(Long cardId) {
        return turnService.getCardById(cardId);
    }

    public List<Turn> getTurns(Long matchId) {
        return turnRepository.findByMatch_IdOrderByTimestampAsc(matchId);
    }

    // Вариант 2: передаём Match
    public List<Turn> getTurns(Match match) {
        if (match == null) return Collections.emptyList();
        return turnRepository.findByMatch_IdOrderByTimestampAsc(match.getId());
    }
}
