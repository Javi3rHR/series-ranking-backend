package com.example.series_ranking.rating.controller;

import com.example.series_ranking.rating.dto.RatingDTO;
import com.example.series_ranking.rating.dto.RatingIdDTO;
import com.example.series_ranking.rating.dto.TopRatedSeriesResponseDTO;
import com.example.series_ranking.rating.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public ResponseEntity<List<RatingDTO>> findAll() {
        List<RatingDTO> ratings = ratingService.findAll();
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/top-rated")
    public ResponseEntity<List<TopRatedSeriesResponseDTO>> getTopRatedSeries() {
        Optional<List<TopRatedSeriesResponseDTO>> topRatedSeries = ratingService.findTopRatedSeries();
        return topRatedSeries.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/series/{seriesId}/user/{userId}")
    public ResponseEntity<RatingDTO> findById(@PathVariable Long seriesId, @PathVariable Long userId) {
        Optional<RatingDTO> ratingDTO = ratingService.findById(new RatingIdDTO(seriesId, userId));
        return ratingDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RatingDTO> save(@Valid @RequestBody RatingDTO ratingDTO) {
        RatingDTO savedRating = ratingService.save(ratingDTO);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RatingDTO> update(@Valid @RequestBody RatingDTO ratingDTO) {
        RatingDTO updatedRating = ratingService.update(ratingDTO);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/series/{seriesId}/user/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long seriesId, @PathVariable Long userId) {
        ratingService.delete(new RatingIdDTO(seriesId, userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
