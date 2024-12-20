package com.example.series_ranking.rating.controller;

import com.example.series_ranking.rating.dto.SeriesDTO;
import com.example.series_ranking.rating.dto.SeriesResponseDTO;
import com.example.series_ranking.rating.service.SeriesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    @Autowired
    private SeriesService seriesService;

    @GetMapping
    public ResponseEntity<List<SeriesResponseDTO>> findAll() {
        List<SeriesResponseDTO> seriesList = seriesService.findAll();
        return new ResponseEntity<>(seriesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponseDTO> findById(@PathVariable Long id) {
        Optional<SeriesResponseDTO> seriesDTO = seriesService.findById(id);
        return seriesDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SeriesDTO> save(@Valid @RequestBody SeriesDTO seriesDTO) {
        SeriesDTO savedSeries = seriesService.save(seriesDTO);
        return new ResponseEntity<>(savedSeries, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeriesDTO> update(@Valid @PathVariable Long id, @RequestBody SeriesDTO seriesDTO) {
        SeriesDTO updatedSeries = seriesService.update(id, seriesDTO);
        return new ResponseEntity<>(updatedSeries, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        seriesService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
