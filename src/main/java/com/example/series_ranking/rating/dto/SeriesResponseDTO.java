package com.example.series_ranking.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesResponseDTO {

    private Long id;
    private String name;
    private String platform;
    private String synopsis;
    private String cover;
    private List<RatingResponseDTO> ratings;


}
