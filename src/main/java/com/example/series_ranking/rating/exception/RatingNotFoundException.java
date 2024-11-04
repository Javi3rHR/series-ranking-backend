package com.example.series_ranking.rating.exception;

import com.example.series_ranking.rating.dto.RatingIdDTO;

public class RatingNotFoundException extends RuntimeException {
    public RatingNotFoundException(RatingIdDTO id) {
        super("Rating with Series ID " + id.getSeriesId() + " and User ID " + id.getUserId() + " not found.");
    }
}
