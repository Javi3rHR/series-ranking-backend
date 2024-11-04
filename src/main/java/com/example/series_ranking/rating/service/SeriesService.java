package com.example.series_ranking.rating.service;


import com.example.series_ranking.rating.dto.SeriesDTO;

import java.util.List;
import java.util.Optional;

public interface SeriesService {
    List<SeriesDTO> findAll();

    Optional<SeriesDTO> findById(Long id);

    SeriesDTO save(SeriesDTO seriesDTO);

    SeriesDTO update(Long id, SeriesDTO seriesDTO);

    void delete(Long id);
}