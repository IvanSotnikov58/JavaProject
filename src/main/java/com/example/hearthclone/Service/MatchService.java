package com.example.hearthclone.Service;

import com.example.hearthclone.model.Cards;
import com.example.hearthclone.model.Decks;
import com.example.hearthclone.model.Match;
import com.example.hearthclone.model.User;
import com.example.hearthclone.Repository.MatchRepository;
import com.example.hearthclone.Repository.DeckRepository;
import com.example.hearthclone.Repository.UserRepository;
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

    public MatchService(MatchRepository matchRepository,
                        DeckRepository deckRepository,
                        UserRepository userRepository,
                        TurnService turnService) {
        this.matchRepository = matchRepository;
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.turnService = turnService;
    }

    // Создать новый матч между двумя игроками
    public Match createMatch(Long player01Id, Long player02Id) {
        Optional<User> player01 = userRepository.findById(player01Id);
        Optional<User> player02 = userRepository.findById(player02Id);

        if (player01.isEmpty() || player02.isEmpty()) return null;

        Match match = new Match(player01.get(), player02.get(), null, "ONGOING");
        match = matchRepository.save(match);

        // Раздать колоды (пока просто берем первую колоду и перемешиваем)
        dealCards(match, player01.get());
        dealCards(match, player02.get());

        return match;
    }

    private void dealCards(Match match, User player) {
        List<Decks> decks = deckRepository.findByPlayerId(player.getId());
        if (decks.isEmpty()) return;

        List<Cards> cards = decks.get(0).getCards();
        Collections.shuffle(cards);

        // Пока просто выводим в консоль или можно сохранить в отдельное поле match (например, map player->cards)
        System.out.println("Разданы карты для " + player.getName() + ": " + cards);
    }

    // Игрок делает ход
    public void playTurn(Match match, User player, Cards card, String target, String result, int turnNumber) {
        turnService.playCard(match, player, card, target, result, turnNumber);

        // Простейшая проверка окончания матча
        if (turnNumber >= 10) { // допустим матч заканчивается после 10 ходов
            match.setStatus("FINISHED");
            matchRepository.save(match);
        }
    }

    public Optional<Match> getMatch(Long matchId) {
        return matchRepository.findById(matchId);
    }

    public Cards getCardFromService(Long cardId) {
        return turnService.getCardById(cardId);
    }
}
