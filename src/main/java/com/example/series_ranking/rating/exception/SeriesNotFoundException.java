package com.example.series_ranking.rating.exception;

public class SeriesNotFoundException extends RuntimeException {
    public SeriesNotFoundException(Long id) {
        super("Series with ID " + id + " not found.");
    }
}
