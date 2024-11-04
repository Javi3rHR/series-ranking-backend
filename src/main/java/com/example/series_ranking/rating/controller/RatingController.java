package com.example.series_ranking.rating.controller;

import com.example.series_ranking.rating.dto.RatingDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<RatingDTO> findById(@PathVariable Long id) {
        Optional<RatingDTO> ratingDTO = ratingService.findById(id);
        return ratingDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<RatingDTO> save(@Valid @RequestBody RatingDTO ratingDTO) {
        RatingDTO savedRating = ratingService.save(ratingDTO);
        return new ResponseEntity<>(savedRating, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RatingDTO> update(@Valid @PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
        RatingDTO updatedRating = ratingService.update(id, ratingDTO);
        return new ResponseEntity<>(updatedRating, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ratingService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
