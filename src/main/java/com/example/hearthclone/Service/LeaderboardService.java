package com.example.hearthclone.Service;

import com.example.hearthclone.dto.UserLeaderboardDto;
import com.example.hearthclone.model.User;
import com.example.hearthclone.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class LeaderboardService {

    private final UserRepository userRepository;

    public LeaderboardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // poluchit top 10 igrokov
    public List<User> getTopPlayers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getRating).reversed())
                .limit(10)
                .toList();
    }
    // zdes poluchit mesto konkretnoho igroka v reitinge, (1...N)
    public int getPlayerRank(Long userId) {
        List<User> sorted = userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getRating).reversed())
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getId().equals(userId)) {
                return i + 1; // потому что индексация с 0
            }
        }
        return -1; // esli igroka netu
    }

    // obnovit reiting igroka
    public User updatePlayerRating(Long userId, int delta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));

        user.setRating(user.getRating() + delta);
        return userRepository.save(user);
    }

    public UserLeaderboardDto getPlayerPosition(Long userId) {
        return null;
    }
}
////