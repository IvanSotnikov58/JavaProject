package com.example.hearthclone.dto;

import java.util.List;

public class DeckRequest {
    private List<Long> cardIds;

    public List<Long> getCardIds() {
        return cardIds;
    }

    public void setCardIds(List<Long> cardIds) {
        this.cardIds = cardIds;
    }
}
