package com.example.hearthclone.dto;

public class UserLeaderboardDto {

    private Long userId;
    private String username;
    private int rating;
    private int place;

    public UserLeaderboardDto(Long userId, String username, int rating, int place) {
        this.userId = userId;
        this.username = username;
        this.rating = rating;
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
}
