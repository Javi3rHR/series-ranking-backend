package com.example.series_ranking.rating.exception;

public class RatingAlreadyExistsException extends RuntimeException {
    public RatingAlreadyExistsException(Long userId, Long seriesId) {
        super("Rating for user with ID " + userId + " and series with ID " + seriesId + " already exists.");
    }
}