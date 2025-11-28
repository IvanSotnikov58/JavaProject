package com.example.hearthclone.dto;

public class MoveRequest {
    private Long matchId;
    private Long playerId;
    private Long cardId;
    private String target;
    private int turnNumber;

    public MoveRequest() {}

    public MoveRequest(Long matchId, Long playerId, Long cardId, String target, int turnNumber) {
        this.matchId = matchId;
        this.playerId = playerId;
        this.cardId = cardId;
        this.target = target;
        this.turnNumber = turnNumber;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
}
