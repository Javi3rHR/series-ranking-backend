package com.example.series_ranking.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SeriesRatingResponseDTO {
    private String seriesName;
    private String platform;
    private String username;
    private Double personalRating;
}
