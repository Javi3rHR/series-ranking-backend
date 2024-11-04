package com.example.series_ranking.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingDTO {

    private Long id;

    @NotNull(message = "Rating cannot be null")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must be at most 10")
    private Double rating;

    @NotNull(message = "Series ID cannot be null")
    private Long seriesId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
