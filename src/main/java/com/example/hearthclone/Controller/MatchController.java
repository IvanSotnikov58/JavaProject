package com.example.hearthclone.Controller;

import com.example.hearthclone.Service.MatchService;
import com.example.hearthclone.dto.MatchStateResponse;
import com.example.hearthclone.model.Match;
import com.example.hearthclone.model.Turn;
import com.example.hearthclone.model.User;
import com.example.hearthclone.model.Cards;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
        System.out.println("Создание матча: player01Id=" + player01Id + ", player02Id=" + player02Id);
        return (Match) matchService.createMatch(player01Id, player02Id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Один из игроков не найден"));
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

        matchService.playTurn(
                matchId,
                playerId,
                "PLAY",       // action
                cardId,
                null,         // targetCardId, если пока не используешь
                null          // targetPlayerId, если пока не используешь
        );

    }

    @GetMapping("/state/{id}")
    public MatchStateResponse getMatchState(@PathVariable Long id) {
        Match match = matchService.getMatch(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Матч не найден"));

        List<Turn> turns = matchService.getTurns(match); // теперь метод Match -> List<Turn>

        return new MatchStateResponse(
                match.getId(),
                match.getPlayer01(),
                match.getPlayer02(),
                turns,
                match.getStatus()
        );
    }
}
