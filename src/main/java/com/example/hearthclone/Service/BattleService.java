package com.example.hearthclone.Service;

import com.example.hearthclone.Repository.*;
import com.example.hearthclone.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BattleService {
    private final MatchRepository matchRepository;
    private final TurnRepository turnRepository;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;
    private final LogRepository logRepository;
    private final UserRepository userRepository;

    public BattleService(MatchRepository matchRepository, TurnRepository turnRepository,
                         DeckRepository deckRepository, CardRepository cardRepository,
                         LogRepository logRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.turnRepository = turnRepository;
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    // nachinaem match
    public Match matchStarts(User player1, User player2) {
        Match match = new Match(player1, player2);
        matchRepository.save(match);

        List<Decks> decksList1 = deckRepository.findByPlayerId(player1.getId());
        List<Decks> decksList2 = deckRepository.findByPlayerId(player2.getId());

        Decks deck1 = decksList1.isEmpty() ? null : decksList1.get(0);
        Decks deck2 = decksList2.isEmpty() ? null : decksList2.get(0);

        match.setDeck1(deck1);
        match.setDeck2(deck2);
        matchRepository.save(match);

        return match;
    }

    // delaem hod
    public Turn playTurn(Long matchId, Long playerId, Long cardId, Long targetPlayerId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Матч не найден"));

        Cards card = cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));

        User player = userRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Игрок не найден"));

        User targetPlayer = userRepository.findById(targetPlayerId)
                .orElseThrow(() -> new RuntimeException("Целевой игрок не найден"));

        // nanosim uron
        int targetHealth = match.getPlayerHealth(targetPlayer);
        targetHealth -= card.getDamage();
        match.setPlayerHealth(targetPlayer, targetHealth);
        matchRepository.save(match);

        // sohranjaem kod
        Turn turn = new Turn();
        turn.setMatch(match);
        turn.setPlayer(player);
        turn.setCard(card);
        turn.setAction("Игрок использовал карту " + card.getName());
        turn.setTimestamp(LocalDateTime.now());
        turnRepository.save(turn);

        // zapisivaem v log
        LogEntry log = new LogEntry(match, "Игрок " + player.getName() + " сыграл карту " + card.getName());
        logRepository.save(log);

        return turn;
    }

    // proverka pobedi
    public User checkWinner(Long matchId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Матч не найден"));

        if (match.getPlayer01Health() <= 0) return match.getPlayer02();
        if (match.getPlayer02Health() <= 0) return match.getPlayer01();
        return null; // pobiditelja netu
    }
}














//package com.example.hearthclone.Service;
//
//import com.example.hearthclone.Repository.*;
//import com.example.hearthclone.model.*;
//import org.springframework.stereotype.Service;
//import com.example.hearthclone.model.Decks;
//
//import javax.smartcardio.Card;
//import java.util.List;
//
//
//@Service
//public class BattleService {
//    private final MatchRepository matchRepository;
//    private final TurnRepository turnRepository;
//    private final DeckRepository deckRepository;
//    private final CardRepository cardRepository;
//    private final LogRepository logRepository;
//
//    // Razdacha kolod
//    public BattleService(MatchRepository matchRepository, TurnRepository turnRepository, DeckRepository deckRepository, CardRepository cardRepository, LogRepository logRepository) {
//        this.matchRepository = matchRepository;
//        this.turnRepository = turnRepository;
//        this.deckRepository = deckRepository;
//        this.cardRepository = cardRepository;
//        this.logRepository = logRepository;
//    }
//    User targetPlayer;
//    if (match.getPlayer01().getId().equals(playerId)) {
//        targetPlayer = match.getPlayer02();
//    } else {
//        targetPlayer = match.getPlayer01();
//    }
//}
//    //Sdelat hod
//    public Match MatchStarts(User player1, User player2) {
//        Match match = new Match(player1, player2);
//        matchRepository.save(match);
//       List<Decks> decksList1= deckRepository.findByPlayerId(player1.getId());
//        List<Decks> decksList2= deckRepository.findByPlayerId(player2.getId());
//        Decks decks1=decksList1.isEmpty() ? null : decksList1.get(0);
//        Decks decks2=decksList2.isEmpty() ? null : decksList2.get(0);
//        match.setDeck1(decks1);
//        match.setDeck2(decks2);
//        matchRepository.save(match);
//        return match;
//    }
//    public Turn PlayTurn(Long matchId, Long playerid, Long targetId) {
//        Match match = matchRepository.findById(matchId).orElseThrow(()->
//                new RuntimeException("Match ne naiden"));
//        //poluchaem Kartu
//
//        Long cardId = 0L;
//        Cards card=cardRepository.findById(cardId)
//                .orElseThrow(()-> new RuntimeException("Karta Ne naidena"));
//
//        //damage ili effekti kakie to
//
//        int targetHealth=match.getPlayerHealth(targetPlayer);
//        targetHealth-=card.getDamage();
//        match.setPlayerHealth(targetPlayer, targetHealth);
//
//        //sohranjaem hod
//        Turn turn= new Turn();
//        turn.setMatch(matchId);
//        turn.setPlayer(playerid);
//        turn.setAction("Igrok ispolzoval kartu" + card.getName());
//
//    }
//    // Записываем в лог
//    LogEntry log = new LogEntry();
//        log.setMatchId(matchId);
//        log.setMessage("Игрок " + playerId + " сыграл карту " + card.getName());
//        logRepository.save(log);
//
//        return turn;
//}
//
//// 3️⃣ Проверка победы
//public User checkWinner(Long matchId) {
//    Match match = matchRepository.findById(matchId).orElseThrow();
//    if (match.getPlayer1().getHealth() <= 0) return match.getPlayer2();
//    if (match.getPlayer2().getHealth() <= 0) return match.getPlayer1();
//    return null; // победителя пока нет
//}
//
//
//
//
