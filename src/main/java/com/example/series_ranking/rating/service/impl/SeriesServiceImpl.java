package com.example.series_ranking.rating.service.impl;

import com.example.series_ranking.rating.dto.SeriesDTO;
import com.example.series_ranking.rating.entity.Series;
import com.example.series_ranking.rating.exception.SeriesAlreadyExistsException;
import com.example.series_ranking.rating.exception.SeriesNotFoundException;
import com.example.series_ranking.rating.repository.SeriesRepository;
import com.example.series_ranking.rating.service.SeriesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public List<SeriesDTO> findAll() {
        try {
            List<Series> seriesList = new ArrayList<>();
            seriesRepository.findAll().iterator().forEachRemaining(seriesList::add);
            return seriesList.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List of series not found.");
        }
    }

    @Override
    public Optional<SeriesDTO> findById(Long id) {
        Optional<Series> seriesOptional = seriesRepository.findById(id);
        return seriesOptional.map(this::convertToDto);
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
                    existingSeries.setName(seriesDTO.getName());
                    existingSeries.setPlatform(seriesDTO.getPlatform());
                    existingSeries.setSynopsis(seriesDTO.getSynopsis());
                    existingSeries.setCover(seriesDTO.getCover());
                    return convertToDto(seriesRepository.save(existingSeries));
                })
                .orElseThrow(() -> new SeriesNotFoundException(id));
    }

    @Override
    public void delete(Long id) {
        if (seriesRepository.existsById(id)) {
            seriesRepository.deleteById(id);
        } else {
            throw new SeriesNotFoundException(id);
        }
    }

    private SeriesDTO convertToDto(Series series) {
        return modelMapper.map(series, SeriesDTO.class);
    }

    private Series convertToEntity(SeriesDTO seriesDTO) {
        return modelMapper.map(seriesDTO, Series.class);
    }
}