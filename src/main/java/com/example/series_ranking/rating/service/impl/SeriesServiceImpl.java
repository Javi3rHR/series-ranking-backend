package com.example.series_ranking.rating.service.impl;

import com.example.series_ranking.rating.dto.RatingResponseDTO;
import com.example.series_ranking.rating.dto.SeriesDTO;
import com.example.series_ranking.rating.dto.SeriesResponseDTO;
import com.example.series_ranking.rating.entity.Series;
import com.example.series_ranking.rating.exception.SeriesAlreadyExistsException;
import com.example.series_ranking.rating.exception.SeriesNotFoundException;
import com.example.series_ranking.rating.repository.SeriesRepository;
import com.example.series_ranking.rating.service.SeriesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeriesServiceImpl implements SeriesService {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<SeriesResponseDTO> findAll() {
        return seriesRepository.findAll()
                .stream()
                .sorted((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName())) // Ordenar alfabéticamente
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<SeriesResponseDTO> findById(Long id) {
        return seriesRepository.findById(id)
                .map(this::convertToResponseDto);
    }

    @Override
    public SeriesDTO save(SeriesDTO seriesDTO) {
        try {
            Series savedSeries = seriesRepository.save(convertToEntity(seriesDTO));
            return convertToDto(savedSeries);
        } catch (DataIntegrityViolationException e) {
            throw new SeriesAlreadyExistsException(seriesDTO.getName());
        }
    }

    @Override
    public SeriesDTO update(Long id, SeriesDTO seriesDTO) {
        return seriesRepository.findById(id)
                .map(existingSeries -> {
                    updateSeriesFromDto(existingSeries, seriesDTO);
                    return convertToDto(seriesRepository.save(existingSeries));
                })
                .orElseThrow(() -> new SeriesNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new SeriesNotFoundException(id);
        }
        seriesRepository.deleteById(id);
    }

    private void updateSeriesFromDto(Series existingSeries, SeriesDTO seriesDTO) {
        existingSeries.setName(seriesDTO.getName());
        existingSeries.setPlatform(seriesDTO.getPlatform());
        existingSeries.setSynopsis(seriesDTO.getSynopsis());
        existingSeries.setCover(seriesDTO.getCover());
    }

    private SeriesDTO convertToDto(Series series) {
        return modelMapper.map(series, SeriesDTO.class);
    }

    private Series convertToEntity(SeriesDTO seriesDTO) {
        return modelMapper.map(seriesDTO, Series.class);
    }

    private SeriesResponseDTO convertToResponseDto(Series series) {
        SeriesResponseDTO seriesResponseDTO = modelMapper.map(series, SeriesResponseDTO.class);
        List<RatingResponseDTO> ratings = series.getRatings()
                .stream()
                .map(rating -> new RatingResponseDTO(rating.getUser().getUsername(), rating.getRating()))
                .collect(Collectors.toList());
        seriesResponseDTO.setRatings(ratings);
        return seriesResponseDTO;
    }
}
