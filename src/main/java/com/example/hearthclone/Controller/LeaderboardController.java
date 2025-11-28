package com.example.hearthclone.Controller;

import com.example.hearthclone.Service.LeaderboardService;
import com.example.hearthclone.dto.UserLeaderboardDto;
import com.example.hearthclone.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    // Получить топ 100 игроков
    @GetMapping("/top")
    public List<User> getTopPlayers() {
        return leaderboardService.getTopPlayers();
    }

    // Получить место и данные конкретного игрока
    @GetMapping("/player/{userId}")
    public UserLeaderboardDto getPlayerPosition(@PathVariable Long userId) {
        return leaderboardService.getPlayerPosition(userId);
    }
}
///