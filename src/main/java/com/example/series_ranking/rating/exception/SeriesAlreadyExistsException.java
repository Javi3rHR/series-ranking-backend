package com.example.series_ranking.rating.exception;

public class SeriesAlreadyExistsException extends RuntimeException {
    public SeriesAlreadyExistsException(String name) {
        super("Series with name '" + name + "' already exists.");
    }
}