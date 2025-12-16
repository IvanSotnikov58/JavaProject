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
import org.springframework.http.ResponseEntity;


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
    public ResponseEntity<?> playTurn(@PathVariable Long matchId,
                                      @RequestParam Long playerId,
                                      @RequestParam Long cardId) {
        try {
            Turn turn = matchService.playTurn(matchId, playerId, "PLAY", cardId, null, null);
            return ResponseEntity.ok(turn);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
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
