package com.example.series_ranking.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopRatedSeriesResponseDTO {
    private String coverImage;
    private String seriesName;
    private String streamingPlatform;
    private String synopsis;
    private Double averageRating;
}