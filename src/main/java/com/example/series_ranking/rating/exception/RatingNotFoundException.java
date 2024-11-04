package com.example.series_ranking.rating.exception;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(Long id) {
        super("Rating with ID " + id + " not found.");
    }
}