package com.example.series_ranking.rating.service.impl;

import com.example.series_ranking.rating.dto.RatingIdDTO;
import com.example.series_ranking.rating.dto.TopRatedSeriesResponseDTO;
import com.example.series_ranking.rating.entity.Rating;
import com.example.series_ranking.rating.entity.RatingId;
import com.example.series_ranking.rating.exception.RatingAlreadyExistsException;
import com.example.series_ranking.rating.repository.RatingRepository;
import com.example.series_ranking.rating.dto.RatingDTO;
import com.example.series_ranking.rating.exception.RatingNotFoundException;
import com.example.series_ranking.rating.repository.SeriesRepository;
import com.example.series_ranking.rating.service.RatingService;
import com.example.series_ranking.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<List<TopRatedSeriesResponseDTO>> findTopRatedSeries() {
        return ratingRepository.findTopRatedSeries();
    }

    @Override
    public Optional<RatingDTO> findById(RatingIdDTO id) {
        Optional<Rating> ratingOptional = ratingRepository.findById(convertToRatingIdEntity(id));
        return ratingOptional.map(this::convertToDto);
    }

    @Override
    @Transactional
    public RatingDTO save(RatingDTO ratingDTO) {
        if (!userRepository.existsById(ratingDTO.getId().getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        if (!seriesRepository.existsById(ratingDTO.getId().getSeriesId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found.");
        }

        RatingId ratingId = convertToRatingIdEntity(ratingDTO.getId());
        if (ratingRepository.existsById(ratingId)) {
            throw new RatingAlreadyExistsException(ratingDTO.getId());
        }

        Rating rating = modelMapper.map(ratingDTO, Rating.class);
        rating.setId(ratingId);
        Rating savedRating = ratingRepository.save(rating);
        return convertToDto(savedRating);
    }

    @Override
    @Transactional
    public RatingDTO update(RatingDTO ratingDTO) {
        if (!userRepository.existsById(ratingDTO.getId().getUserId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.");
        }
        if (!seriesRepository.existsById(ratingDTO.getId().getSeriesId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Series not found.");
        }

        Rating rating = ratingRepository.findById(convertToRatingIdEntity(ratingDTO.getId()))
                .orElseThrow(() -> new RatingNotFoundException(ratingDTO.getId()));

        rating.setRating(ratingDTO.getRating());

        Rating updatedRating = ratingRepository.save(rating);
        return convertToDto(updatedRating);
    }

    @Override
    public void delete(RatingIdDTO id) {
        Rating rating = ratingRepository.findById(convertToRatingIdEntity(id))
                .orElseThrow(() -> new RatingNotFoundException(id));
        ratingRepository.delete(rating);
    }

    private RatingDTO convertToDto(Rating rating) {
        return modelMapper.map(rating, RatingDTO.class);
    }

    private RatingId convertToRatingIdEntity(RatingIdDTO id) {
        return modelMapper.map(id, RatingId.class);
    }
}
