package com.example.hearthclone.dto;

public class MoveRequest {
    private Long playerId;       // кто делает действие
    private String action;       // "PLAY" или "ATTACK"
    private Long cardId;         // id карты (для PLAY — карта из руки, для ATTACK — id карты-на-поле (оригинальный card id))
    private Long targetCardId;   // id карты противника на поле (nullable)
    private Long targetPlayerId; // если атака по игроку (nullable)

    public MoveRequest() {}

    public Long getPlayerId() { return playerId; }
    public void setPlayerId(Long playerId) { this.playerId = playerId; }

    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }

    public Long getCardId() { return cardId; }
    public void setCardId(Long cardId) { this.cardId = cardId; }

    public Long getTargetCardId() { return targetCardId; }
    public void setTargetCardId(Long targetCardId) { this.targetCardId = targetCardId; }

    public Long getTargetPlayerId() { return targetPlayerId; }
    public void setTargetPlayerId(Long targetPlayerId) { this.targetPlayerId = targetPlayerId; }
}
