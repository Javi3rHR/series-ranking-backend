package com.example.series_ranking.rating.service.impl;

import com.example.series_ranking.rating.entity.Rating;
import com.example.series_ranking.rating.entity.Series;
import com.example.series_ranking.rating.exception.RatingAlreadyExistsException;
import com.example.series_ranking.rating.exception.SeriesNotFoundException;
import com.example.series_ranking.rating.repository.RatingRepository;
import com.example.series_ranking.rating.dto.RatingDTO;
import com.example.series_ranking.rating.exception.RatingNotFoundException;
import com.example.series_ranking.rating.repository.SeriesRepository;
import com.example.series_ranking.rating.service.RatingService;
import com.example.series_ranking.user.User;
import com.example.series_ranking.user.UserRepository;
import com.example.series_ranking.user.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class RatingServiceImpl implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<RatingDTO> findAll() {
        try {
            List<Rating> ratings = new ArrayList<>();
            ratingRepository.findAll().iterator().forEachRemaining(ratings::add);
            return ratings.stream().map(this::convertToDto).collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "List of ratings not found.");
        }
    }

    @Override
    public Optional<RatingDTO> findById(Long id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(id);
        return ratingOptional.map(this::convertToDto);
    }

    @Override
    @Transactional
    public RatingDTO save(RatingDTO ratingDTO) {
        try {
            Rating rating = convertToEntity(ratingDTO);
            rating.setSeries(getSeries(ratingDTO.getSeriesId()));
            rating.setUser(getUser(ratingDTO.getUserId()));
            Rating savedRating = ratingRepository.save(rating);
            return convertToDto(savedRating);
        } catch (DataIntegrityViolationException e) {
            throw new RatingAlreadyExistsException(ratingDTO.getUserId(), ratingDTO.getSeriesId());
        }
    }

    private Series getSeries(Long seriesId) {
        return seriesRepository.findById(seriesId)
                .orElseThrow(() -> new SeriesNotFoundException(seriesId));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    @Transactional
    public RatingDTO update(Long id, RatingDTO ratingDTO) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException(id));

        rating.setRating(ratingDTO.getRating());
        rating.setSeries(getSeries(ratingDTO.getSeriesId()));
        rating.setUser(getUser(ratingDTO.getUserId()));

        Rating updatedRating = ratingRepository.save(rating);
        return convertToDto(updatedRating);
    }

    @Override
    public void delete(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
        } else {
            throw new RatingNotFoundException(id);
        }
    }

    private RatingDTO convertToDto(Rating rating) {
        return modelMapper.map(rating, RatingDTO.class);
    }

    private Rating convertToEntity(RatingDTO ratingDTO) {
        return modelMapper.map(ratingDTO, Rating.class);
    }
}