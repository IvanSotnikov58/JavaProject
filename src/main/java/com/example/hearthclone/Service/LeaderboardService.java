package com.example.hearthclone.Service;

import com.example.hearthclone.dto.UserLeaderboardDto;
import com.example.hearthclone.model.User;
import com.example.hearthclone.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class LeaderboardService {
    private final UserRepository userRepository;

    public LeaderboardService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getTopPlayers() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getRating).reversed())
                .limit(10)
                .toList();
    }

    public int getPlayerRank(Long userId) {
        List<User> sorted = userRepository.findAll().stream()
                .sorted(Comparator.comparingInt(User::getRating).reversed())
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getId().equals(userId)) {
                return i + 1;
            }
        }
        return -1;
    }

    public User updatePlayerRating(Long userId, int delta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
        user.setRating(user.getRating() + delta);
        return userRepository.save(user);
    }

    public UserLeaderboardDto getPlayerPosition(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return null;
        }

        User user = userOpt.get();
        int rank = getPlayerRank(userId);

        return new UserLeaderboardDto(user.getId(), user.getName(), user.getRating(), rank);
    }
}