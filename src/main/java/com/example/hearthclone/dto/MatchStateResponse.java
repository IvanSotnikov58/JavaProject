package com.example.hearthclone.dto;

import com.example.hearthclone.model.Cards;
import com.example.hearthclone.model.Turn;
import com.example.hearthclone.model.User;

import java.util.List;

public class MatchStateResponse {
    private Long matchId;
    private User player01;
    private User player02;
    private List<Turn> turns;
    private String status;

    public MatchStateResponse() {}

    public MatchStateResponse(Long matchId, User player01, User player02, List<Turn> turns, String status) {
        this.matchId = matchId;
        this.player01 = player01;
        this.player02 = player02;
        this.turns = turns;
        this.status = status;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public User getPlayer01() {
        return player01;
    }

    public void setPlayer01(User player01) {
        this.player01 = player01;
    }

    public User getPlayer02() {
        return player02;
    }

    public void setPlayer02(User player02) {
        this.player02 = player02;
    }

    public List<Turn> getTurns() {
        return turns;
    }

    public void setTurns(List<Turn> turns) {
        this.turns = turns;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
