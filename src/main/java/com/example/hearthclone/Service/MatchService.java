package com.example.hearthclone.Service;

import com.example.hearthclone.Repository.*;
import com.example.hearthclone.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final DeckRepository deckRepository;
    private final UserRepository userRepository;
    private final TurnRepository turnRepository;
    private final CardRepository cardRepository;

    // В памяти храним состояние матча (учебный вариант)
    private final Map<Long, List<InPlayCard>> fieldP1 = new ConcurrentHashMap<>();
    private final Map<Long, List<InPlayCard>> fieldP2 = new ConcurrentHashMap<>();
    private final Map<Long, List<Cards>> handP1 = new ConcurrentHashMap<>();
    private final Map<Long, List<Cards>> handP2 = new ConcurrentHashMap<>();
    private final Map<Long, Queue<Cards>> deckP1 = new ConcurrentHashMap<>();
    private final Map<Long, Queue<Cards>> deckP2 = new ConcurrentHashMap<>();
    private final Map<Long, Long> currentPlayer = new ConcurrentHashMap<>(); // matchId -> playerId
    private final Map<Long, Integer> turnCounter = new ConcurrentHashMap<>(); // matchId -> number

    private final Random rnd = new Random();

    public MatchService(MatchRepository matchRepository,
                        DeckRepository deckRepository,
                        UserRepository userRepository,
                        TurnRepository turnRepository,
                        CardRepository cardRepository) {
        this.matchRepository = matchRepository;
        this.deckRepository = deckRepository;
        this.userRepository = userRepository;
        this.turnRepository = turnRepository;
        this.cardRepository = cardRepository;
    }

    // Вспомогательный класс: карта на поле с текущим здоровьем
    private static class InPlayCard {
        final String instanceId; // уникальный для этого экземпляра карты
        final Cards base;
        int currentHealth;

        InPlayCard(Cards base) {
            this.instanceId = UUID.randomUUID().toString();
            this.base = base;
            this.currentHealth = base.getHealth();
        }
    }

    public Cards getCardFromService(Long cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Карта не найдена"));
    }

    // ================== Создание матча ==================
    public Optional<Match> createMatch(Long player01Id, Long player02Id) {
        User p1 = userRepository.findById(player01Id).orElse(null);
        User p2 = userRepository.findById(player02Id).orElse(null);
        if (p1 == null || p2 == null) return Optional.empty();

        p1.setHealth(100);
        p2.setHealth(100);
        userRepository.save(p1);
        userRepository.save(p2);

        Match match = new Match(p1, p2, null, "ONGOING");
        match = matchRepository.save(match);

        initPlayerState(match.getId(), p1.getId(), true);
        initPlayerState(match.getId(), p2.getId(), false);

        currentPlayer.put(match.getId(), p1.getId());
        turnCounter.put(match.getId(), 1);

        return Optional.of(match);
    }

    private void initPlayerState(Long matchId, Long playerId, boolean isP1) {
        List<Decks> decks = deckRepository.findByPlayerId(playerId);
        List<Cards> deckCards = decks.isEmpty() ? new ArrayList<>() : new ArrayList<>(decks.get(0).getCards());
        Collections.shuffle(deckCards);

        Queue<Cards> deckQueue = new LinkedList<>(deckCards);
        List<Cards> hand = new ArrayList<>();
        List<InPlayCard> field = new ArrayList<>();

        for (int i = 0; i < 3 && !deckQueue.isEmpty(); i++) {
            hand.add(deckQueue.poll());
        }

        if (isP1) {
            deckP1.put(matchId, deckQueue);
            handP1.put(matchId, hand);
            fieldP1.put(matchId, field);
        } else {
            deckP2.put(matchId, deckQueue);
            handP2.put(matchId, hand);
            fieldP2.put(matchId, field);
        }
    }

    // ================== Основная логика хода ==================
    public Turn playTurn(Long matchId, Long playerId, String action, Long cardId, Long targetCardId, Long targetPlayerId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Матч не найден"));

        if (!"ONGOING".equals(match.getStatus())) throw new RuntimeException("Матч завершён");

        Long cur = currentPlayer.get(matchId);
        if (cur == null || !cur.equals(playerId)) throw new RuntimeException("Сейчас не ваш ход");

        boolean isP1 = match.getPlayer01().getId().equals(playerId);
        List<Cards> hand = isP1 ? handP1.get(matchId) : handP2.get(matchId);
        Queue<Cards> deck = isP1 ? deckP1.get(matchId) : deckP2.get(matchId);
        List<InPlayCard> myField = isP1 ? fieldP1.get(matchId) : fieldP2.get(matchId);
        List<InPlayCard> oppField = isP1 ? fieldP2.get(matchId) : fieldP1.get(matchId);
        User attacker = isP1 ? match.getPlayer01() : match.getPlayer02();
        User defender = isP1 ? match.getPlayer02() : match.getPlayer01();

        if (!"PLAY".equalsIgnoreCase(action)) throw new RuntimeException("Поддерживается только действие PLAY");

        Cards cardToPlay = hand.stream()
                .filter(c -> c.getId().equals(cardId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Карта не найдена в руке"));

        InPlayCard myCardInstance = new InPlayCard(cardToPlay);
        myField.add(myCardInstance);
        hand.remove(cardToPlay);

        String resultDesc;

        if (oppField != null && !oppField.isEmpty()) {
            int idx = rnd.nextInt(oppField.size());
            InPlayCard target = oppField.get(idx);

            int dmg = myCardInstance.base.getDamage();
            int dmgBack = target.base.getDamage();

            target.currentHealth -= dmg;
            myCardInstance.currentHealth -= dmgBack;

            resultDesc = String.format("%s: карта %s (HP:%d) ударила %s: карту %s (HP:%d -> %d); ответный урон %d, HP атакующей -> %d",
                    attacker.getName(),
                    myCardInstance.base.getName(),
                    myCardInstance.base.getHealth(),
                    defender.getName(),
                    target.base.getName(),
                    target.base.getHealth(),
                    Math.max(0, target.currentHealth),
                    dmgBack,
                    Math.max(0, myCardInstance.currentHealth)
            );

            if (target.currentHealth <= 0) oppField.remove(target);
            if (myCardInstance.currentHealth <= 0) myField.remove(myCardInstance);

        } else {
            int dmg = myCardInstance.base.getDamage();
            if (match.getPlayer01().getId().equals(defender.getId())) {
                match.setPlayerHealth(match.getPlayer01(), match.getPlayerHealth(match.getPlayer01()) - dmg);
            } else {
                match.setPlayerHealth(match.getPlayer02(), match.getPlayerHealth(match.getPlayer02()) - dmg);
            }

            resultDesc = String.format("%s: карта %s нанесла %d урона игроку %s (HP -> %d)",
                    attacker.getName(),
                    myCardInstance.base.getName(),
                    dmg,
                    defender.getName(),
                    match.getPlayerHealth(defender)
            );

            if (match.getPlayerHealth(defender) <= 0) {
                match.setWinner(attacker);
                match.setStatus("FINISHED");
                matchRepository.save(match);
            }
        }

        if (deck != null && !deck.isEmpty()) {
            Cards drawn = deck.poll();
            hand.add(drawn);
        }

        Turn saved = makeAndSaveTurn(match, attacker, cardToPlay, "PLAY", resultDesc);

        Long other = match.getPlayer01().getId().equals(playerId) ? match.getPlayer02().getId() : match.getPlayer01().getId();
        currentPlayer.put(matchId, other);
        turnCounter.put(matchId, turnCounter.getOrDefault(matchId, 1) + 1);

        matchRepository.save(match);

        return saved;
    }

    private Turn makeAndSaveTurn(Match match, User player, Cards card, String action, String description) {
        Turn t = new Turn();
        t.setMatch(match);
        t.setPlayer(player);
        t.setCard(card);
        t.setAction(action);
        t.setResult(description);
        t.setTimestamp(LocalDateTime.now());
        t.setTurnNumber(turnCounter.getOrDefault(match.getId(), 1));
        return turnRepository.save(t);
    }

    // ================== Доп. методы ==================
    public Optional<Match> getMatch(Long matchId) {
        return matchRepository.findById(matchId);
    }

    public List<Turn> getTurns(Long matchId) {
        return turnRepository.findByMatch_IdOrderByTimestampAsc(matchId);
    }

    public List<Turn> getTurns(Match match) {
        if (match == null) return Collections.emptyList();
        return getTurns(match.getId());
    }

    public List<Cards> getHand(Long matchId, Long playerId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Матч не найден"));

        List<Cards> hand = match.getPlayer01().getId().equals(playerId) ? handP1.get(matchId) : handP2.get(matchId);
        return hand == null ? List.of() : List.copyOf(hand);
    }

    public List<Map<String, Object>> getFieldView(Long matchId, Long playerId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Матч не найден"));

        List<InPlayCard> field = match.getPlayer01().getId().equals(playerId) ? fieldP1.get(matchId) : fieldP2.get(matchId);
        if (field == null) return List.of();

        List<Map<String, Object>> out = new ArrayList<>();
        for (InPlayCard ipc : field) {
            Map<String, Object> map = new HashMap<>();
            map.put("instanceId", ipc.instanceId);
            map.put("cardId", ipc.base.getId());
            map.put("name", ipc.base.getName());
            map.put("health", ipc.currentHealth);
            map.put("damage", ipc.base.getDamage());
            out.add(map);
        }
        return out;
    }
}
