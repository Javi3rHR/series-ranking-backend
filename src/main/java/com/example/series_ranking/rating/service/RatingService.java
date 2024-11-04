package com.example.series_ranking.rating.service;

import com.example.series_ranking.rating.dto.RatingDTO;
import com.example.series_ranking.rating.dto.RatingIdDTO;
import com.example.series_ranking.rating.dto.TopRatedSeriesResponseDTO;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<RatingDTO> findAll();

    Optional<List<TopRatedSeriesResponseDTO>> findTopRatedSeries();

    Optional<RatingDTO> findById(RatingIdDTO ratingId);

    RatingDTO save(RatingDTO ratingDTO);

    RatingDTO update(RatingDTO ratingDTO);

    void delete(RatingIdDTO ratingDTO);
}