package com.example.series_ranking.rating.service.impl;

import com.example.series_ranking.rating.dto.SeriesDTO;
import com.example.series_ranking.rating.dto.SeriesResponseDTO;
import com.example.series_ranking.rating.entity.Series;
import com.example.series_ranking.rating.exception.SeriesAlreadyExistsException;
import com.example.series_ranking.rating.exception.SeriesNotFoundException;
import com.example.series_ranking.rating.repository.SeriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SeriesServiceImplTest {

    @InjectMocks
    private SeriesServiceImpl seriesService;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Series> seriesList = new ArrayList<>();
        Series series1 = new Series();
        series1.setName("Series A");
        series1.setRatings(new ArrayList<>());
        Series series2 = new Series();
        series2.setName("Series B");
        series2.setRatings(new ArrayList<>());
        seriesList.add(series1);
        seriesList.add(series2);

        when(seriesRepository.findAll()).thenReturn(seriesList);
        when(modelMapper.map(any(Series.class), eq(SeriesResponseDTO.class))).thenReturn(new SeriesResponseDTO());

        // Act
        List<SeriesResponseDTO> result = seriesService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(seriesRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        // Arrange
        Series series = new Series();
        series.setName("Series A");
        series.setRatings(new ArrayList<>());
        SeriesResponseDTO seriesResponseDTO = new SeriesResponseDTO();
        seriesResponseDTO.setName("Series A");

        when(seriesRepository.findById(1L)).thenReturn(Optional.of(series));
        when(modelMapper.map(any(Series.class), eq(SeriesResponseDTO.class))).thenReturn(seriesResponseDTO);

        // Act
        Optional<SeriesResponseDTO> result = seriesService.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Series A", result.get().getName());
        verify(seriesRepository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        // Arrange
        SeriesDTO seriesDTO = new SeriesDTO();
        seriesDTO.setName("Series A");

        Series series = new Series();
        series.setName("Series A");
        series.setRatings(new ArrayList<>());

        when(modelMapper.map(any(SeriesDTO.class), eq(Series.class))).thenReturn(series);
        when(seriesRepository.save(any(Series.class))).thenReturn(series);
        when(modelMapper.map(any(Series.class), eq(SeriesDTO.class))).thenReturn(seriesDTO);

        // Act
        SeriesDTO result = seriesService.save(seriesDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Series A", result.getName());
        verify(seriesRepository, times(1)).save(any(Series.class));
    }

    @Test
    void testSave_SeriesAlreadyExists() {
        // Arrange
        SeriesDTO seriesDTO = new SeriesDTO();
        seriesDTO.setName("Series A");

        when(modelMapper.map(any(SeriesDTO.class), eq(Series.class))).thenReturn(new Series());
        when(seriesRepository.save(any(Series.class))).thenThrow(new DataIntegrityViolationException(""));

        // Act & Assert
        assertThrows(SeriesAlreadyExistsException.class, () -> seriesService.save(seriesDTO));
    }

    @Test
    void testUpdate() {
        // Arrange
        Long seriesId = 1L;
        SeriesDTO seriesDTO = new SeriesDTO();
        seriesDTO.setName("Updated Series A");

        Series existingSeries = new Series();
        existingSeries.setName("Series A");
        existingSeries.setRatings(new ArrayList<>());

        when(seriesRepository.findById(seriesId)).thenReturn(Optional.of(existingSeries));
        when(modelMapper.map(any(Series.class), eq(SeriesDTO.class))).thenReturn(seriesDTO);
        when(seriesRepository.save(existingSeries)).thenReturn(existingSeries);

        // Act
        SeriesDTO result = seriesService.update(seriesId, seriesDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Series A", result.getName());
        verify(seriesRepository, times(1)).save(existingSeries);
    }

    @Test
    void testUpdate_SeriesNotFound() {
        // Arrange
        SeriesDTO seriesDTO = new SeriesDTO();
        seriesDTO.setName("Updated Series A");

        when(seriesRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SeriesNotFoundException.class, () -> seriesService.update(1L, seriesDTO));
    }

    @Test
    void testDelete() {
        // Arrange
        Long seriesId = 1L;
        when(seriesRepository.existsById(seriesId)).thenReturn(true);

        // Act
        seriesService.delete(seriesId);

        // Assert
        verify(seriesRepository, times(1)).deleteById(seriesId);
    }

    @Test
    void testDelete_SeriesNotFound() {
        // Arrange
        Long seriesId = 1L;
        when(seriesRepository.existsById(seriesId)).thenReturn(false);

        // Act & Assert
        assertThrows(SeriesNotFoundException.class, () -> seriesService.delete(seriesId));
    }
}
