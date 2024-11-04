package com.example.series_ranking.rating.service;

import com.example.series_ranking.rating.dto.RatingDTO;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    List<RatingDTO> findAll();

    Optional<RatingDTO> findById(Long id);

    RatingDTO save(RatingDTO ratingDTO);

    RatingDTO update(Long id, RatingDTO ratingDTO);

    void delete(Long id);
}