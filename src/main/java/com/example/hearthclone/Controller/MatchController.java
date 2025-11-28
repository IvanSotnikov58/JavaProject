package com.example.hearthclone.Controller;

import com.example.hearthclone.Service.MatchService;
import com.example.hearthclone.model.Match;
import com.example.hearthclone.model.User;
import com.example.hearthclone.model.Cards;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    // Создать матч
    @PostMapping
    public Match createMatch(@RequestParam Long player01Id, @RequestParam Long player02Id) {
        return matchService.createMatch(player01Id, player02Id);
    }

    // Получить матч по id
    @GetMapping("/{id}")
    public Optional<Match> getMatch(@PathVariable Long id) {
        return matchService.getMatch(id);
    }

    // Игрок делает ход
    @PostMapping("/{matchId}/play")
    public void playTurn(@PathVariable Long matchId,
                         @RequestParam Long playerId,
                         @RequestParam Long cardId,
                         @RequestParam String target,
                         @RequestParam String result,
                         @RequestParam int turnNumber) {

        Optional<Match> matchOpt = matchService.getMatch(matchId);
        if (matchOpt.isEmpty()) return;

        Match match = matchOpt.get();
        User player = match.getPlayer01().getId().equals(playerId) ? match.getPlayer01() : match.getPlayer02();
        Cards card = matchService.getCardFromService(cardId);

        matchService.playTurn(match, player, card, target, result, turnNumber);
    }
}
