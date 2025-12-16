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

    // Мана: разделяем на player01 / player02 по matchId
    private final Map<Long, Integer> maxManaP1 = new ConcurrentHashMap<>();
    private final Map<Long, Integer> currentManaP1 = new ConcurrentHashMap<>();
    private final Map<Long, Integer> maxManaP2 = new ConcurrentHashMap<>();
    private final Map<Long, Integer> currentManaP2 = new ConcurrentHashMap<>();

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

    // Вспомогательный класс: карта на поле с текущим здоровьем и эффектами
    private static class InPlayCard {
        final String instanceId; // уникальный для этого экземпляра карты
        final Cards base;
        int currentHealth;

        // эффекты/статусы
        int bonusDamage = 0;   // временный бонус урона
        boolean taunt = false; // приманка
        int invulTurns = 0;    // количество атак, которые игнорируются

        InPlayCard(Cards base) {
            this.instanceId = UUID.randomUUID().toString();
            this.base = base;
            this.currentHealth = base.getHealth();
        }
    }

    // Утилита получение карты по id
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

        // Инициализируем ману:
        // теперь оба стартуют с 1/1
        maxManaP1.put(match.getId(), 1);
        currentManaP1.put(match.getId(), 1);
        maxManaP2.put(match.getId(), 1);
        currentManaP2.put(match.getId(), 1);

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

    // ================== Управление маной ==================
    /**
     * На начало хода:
     * - увеличиваем max mana на 1 (до 10)
     * - добавляем +1 к current mana, но не выше max
     */
    private void startTurnFor(Long matchId, boolean isP1) {
        if (isP1) {
            int oldMax = maxManaP1.getOrDefault(matchId, 0);
            int newMax = Math.min(10, oldMax + 1);
            maxManaP1.put(matchId, newMax);

            int oldCur = currentManaP1.getOrDefault(matchId, 0);
            int newCur = Math.min(newMax, oldCur + 1);
            currentManaP1.put(matchId, newCur);
        } else {
            int oldMax = maxManaP2.getOrDefault(matchId, 0);
            int newMax = Math.min(10, oldMax + 1);
            maxManaP2.put(matchId, newMax);

            int oldCur = currentManaP2.getOrDefault(matchId, 0);
            int newCur = Math.min(newMax, oldCur + 1);
            currentManaP2.put(matchId, newCur);
        }
    }

    // Возвращает текущее количество маны игрока в матче
    public int getCurrentMana(Long matchId, Long playerId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null) return 0;
        boolean isP1 = match.getPlayer01().getId().equals(playerId);
        return isP1 ? currentManaP1.getOrDefault(matchId, 0) : currentManaP2.getOrDefault(matchId, 0);
    }

    // Возвращает max маны игрока в матче
    public int getMaxMana(Long matchId, Long playerId) {
        Match match = matchRepository.findById(matchId).orElse(null);
        if (match == null) return 0;
        boolean isP1 = match.getPlayer01().getId().equals(playerId);
        return isP1 ? maxManaP1.getOrDefault(matchId, 0) : maxManaP2.getOrDefault(matchId, 0);
    }

    // ================== Основная логика хода (PLAY с эффектами и маной) ==================
    /**
     * action: пока поддерживается только "PLAY" — положить карту и сразу применить её эффект/атаку.
     * cardId — id карты в руке.
     * targetCardId/targetPlayerId игнорируются в этой реализации (сервер сам выбирает цель).
     */
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

        // 0) Проверяем ману
        int cost;
        Cards peek = hand == null ? null : hand.stream().filter(c -> c.getId().equals(cardId)).findFirst().orElse(null);
        if (peek == null) throw new RuntimeException("Карта не найдена в руке");
        cost = peek.getCost();
        int available = isP1 ? currentManaP1.getOrDefault(matchId, 0) : currentManaP2.getOrDefault(matchId, 0);
        if (available < cost) {
            throw new RuntimeException("Недостаточно маны: нужно " + cost + ", есть " + available);
        }

        // 1) Берём карту из руки (карта уже найдена в peek)
        Cards cardToPlay = peek;

        // Снимаем ману
        if (isP1) {
            currentManaP1.put(matchId, available - cost);
        } else {
            currentManaP2.put(matchId, available - cost);
        }

        // 2) Создаём инстанс на поле и применяем эффекты, если есть
        InPlayCard myCardInstance = new InPlayCard(cardToPlay);

        // разбор эффектов (effectKey / effectPayload). допускаем null и "none"
        String key = cardToPlay.getEffectKey();
        String payload = cardToPlay.getEffectPayload();

        if (key != null && !"none".equalsIgnoreCase(key)) {
            switch (key) {
                case "INVULN": // payload = число атак, обычно "1"
                    try {
                        myCardInstance.invulTurns = Math.max(1, Integer.parseInt(payload));
                    } catch (Exception e) {
                        myCardInstance.invulTurns = 1;
                    }
                    break;
                case "BUFF_ALL_DAMAGE": // payload = число (например "1")
                    int buff = 0;
                    try {
                        buff = Integer.parseInt(payload);
                    } catch (Exception ignored) {}
                    // применяем к уже существующим картам на поле (включая эту карту после добавления)
                    List<InPlayCard> targetField = myField != null ? myField : new ArrayList<>();
                    for (InPlayCard ipc : targetField) {
                        ipc.bonusDamage += buff;
                    }
                    // также добавим бафф к этой карте после добавления
                    myCardInstance.bonusDamage += buff;
                    break;
                case "TAUNT":
                    myCardInstance.taunt = true;
                    break;
                default:
                    // неизвестный эффект — игнорируем (не будем кидать ошибку)
                    break;
            }
        }

        // ложим на поле и убираем из руки
        if (myField == null) myField = new ArrayList<>();
        myField.add(myCardInstance);
        if (isP1) fieldP1.put(matchId, myField); else fieldP2.put(matchId, myField);
        hand.remove(cardToPlay);

        String resultDesc;

        // 3) Выбор цели: если на поле противника есть таунт — выбираем из них, иначе случайная карта
        InPlayCard chosenTarget = null;
        if (oppField != null && !oppField.isEmpty()) {
            // сначала ищем все taunt
            List<InPlayCard> taunts = new ArrayList<>();
            for (InPlayCard ipc : oppField) if (ipc.taunt) taunts.add(ipc);

            if (!taunts.isEmpty()) {
                chosenTarget = taunts.get(rnd.nextInt(taunts.size()));
            } else {
                chosenTarget = oppField.get(rnd.nextInt(oppField.size()));
            }
        }

        // 4) Выполняем атаку
        if (chosenTarget != null) {
            // атакуем карту противника
            int dmg = myCardInstance.base.getDamage() + myCardInstance.bonusDamage;
            int dmgBack = chosenTarget.base.getDamage() + chosenTarget.bonusDamage;

            // Если цель имеет неуязвимость — она не получает урон, но даёт ответный урон
            if (chosenTarget.invulTurns > 0) {
                chosenTarget.invulTurns = Math.max(0, chosenTarget.invulTurns - 1);
                // атакующая карта получает ответный урон, цель остаётся жива
                myCardInstance.currentHealth -= dmgBack;
                resultDesc = String.format("%s: карта %s ударила %s: карту %s, но та была неуязвима (защитилось %d), атакующая получила ответный урон %d (HP -> %d)",
                        attacker.getName(), myCardInstance.base.getName(),
                        defender.getName(), chosenTarget.base.getName(),
                        chosenTarget.invulTurns, dmgBack, Math.max(0, myCardInstance.currentHealth));
            } else {
                // оба получают урон друг от друга
                chosenTarget.currentHealth -= dmg;
                myCardInstance.currentHealth -= dmgBack;
                resultDesc = String.format("%s: карта %s нанесла %d урона карте %s (HP -> %d); ответный урон %d, HP атакующей -> %d",
                        attacker.getName(), myCardInstance.base.getName(), dmg,
                        defender.getName(), Math.max(0, chosenTarget.currentHealth),
                        dmgBack, Math.max(0, myCardInstance.currentHealth));
            }

            // удаляем мёртвые карты с поля
            if (chosenTarget.currentHealth <= 0 && oppField != null) oppField.remove(chosenTarget);
            if (myCardInstance.currentHealth <= 0 && myField != null) myField.remove(myCardInstance);

        } else {
            // атакуем игрока напрямую
            int dmg = myCardInstance.base.getDamage() + myCardInstance.bonusDamage;
            if (match.getPlayer01().getId().equals(defender.getId())) {
                match.setPlayerHealth(match.getPlayer01(), match.getPlayerHealth(match.getPlayer01()) - dmg);
            } else {
                match.setPlayerHealth(match.getPlayer02(), match.getPlayerHealth(match.getPlayer02()) - dmg);
            }

            resultDesc = String.format("%s: карта %s нанесла %d урона игроку %s (HP -> %d)",
                    attacker.getName(), myCardInstance.base.getName(), dmg,
                    defender.getName(), match.getPlayerHealth(defender));

            if (match.getPlayerHealth(defender) <= 0) {
                match.setWinner(attacker);
                match.setStatus("FINISHED");
                matchRepository.save(match);
            }
        }

        // 5) Добор карты (если колода пустая — даём случайную из всех карт, имитация бесконечной колоды)
        if (deck != null && !deck.isEmpty()) {
            Cards drawn = deck.poll();
            hand.add(drawn);
        } else {
            List<Cards> all = cardRepository.findAll();
            if (!all.isEmpty()) {
                Cards randomCard = all.get(rnd.nextInt(all.size()));
                hand.add(randomCard);
            }
        }

        // 6) Сохраняем ход в Turn
        Turn saved = makeAndSaveTurn(match, attacker, cardToPlay, "PLAY", resultDesc);

        // 7) Переключаем ход: даём ману следующему игроку (startTurnFor) и увеличиваем счетчик
        Long other = match.getPlayer01().getId().equals(playerId) ? match.getPlayer02().getId() : match.getPlayer01().getId();
        currentPlayer.put(matchId, other);

        // startTurnFor на следующего игрока (увеличивает max и добавляет +1 к current, не выше max)
        boolean otherIsP1 = match.getPlayer01().getId().equals(other);
        startTurnFor(matchId, otherIsP1);

        turnCounter.put(matchId, turnCounter.getOrDefault(matchId, 1) + 1);

        // 8) Сохраняем match (возможно изменилось здоровье или установлен победитель)
        matchRepository.save(match);

        return saved;
    }

    // ============== Хелпер: создать и сохранить Turn ==============
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

    // ================== Доп. методы для фронта/отладки ==================
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
            map.put("damage", ipc.base.getDamage() + ipc.bonusDamage);
            map.put("taunt", ipc.taunt);
            map.put("invulTurns", ipc.invulTurns);
            out.add(map);
        }
        return out;
    }
}
