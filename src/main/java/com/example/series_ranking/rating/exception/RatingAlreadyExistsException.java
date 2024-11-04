package com.example.series_ranking.rating.exception;

import com.example.series_ranking.rating.dto.RatingIdDTO;

public class RatingAlreadyExistsException extends RuntimeException {
    public RatingAlreadyExistsException(RatingIdDTO id) {
        super("Rating for user with ID " + id.getUserId() + " and series with ID " + id.getSeriesId() + " already exists.");
    }
}