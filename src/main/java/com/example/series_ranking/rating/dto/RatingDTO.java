package com.example.series_ranking.rating.dto;

import lombok.Data;

@Data
public class RatingDTO {

    private Long id;
    private Double rating;
    private Long seriesId;
    private Long userId;
}