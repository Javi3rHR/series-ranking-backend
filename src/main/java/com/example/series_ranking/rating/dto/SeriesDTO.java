package com.example.series_ranking.rating.dto;

import lombok.Data;

@Data
public class SeriesDTO {

    private Long id;
    private String name;
    private String platform;
    private String synopsis;
    private String cover;
}