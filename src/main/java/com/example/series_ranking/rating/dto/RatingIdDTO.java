package com.example.series_ranking.rating.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingIdDTO {

    @NotNull(message = "Series ID cannot be null")
    private Long seriesId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}