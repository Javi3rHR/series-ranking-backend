package com.example.series_ranking.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponseDTO {
    private String username;
    private Double personalRating;
}