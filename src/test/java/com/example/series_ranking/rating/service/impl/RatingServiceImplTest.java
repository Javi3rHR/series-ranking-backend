package com.example.series_ranking.rating.service.impl;

import com.example.series_ranking.rating.dto.RatingDTO;
import com.example.series_ranking.rating.dto.RatingIdDTO;
import com.example.series_ranking.rating.dto.TopRatedSeriesResponseDTO;
import com.example.series_ranking.rating.entity.Rating;
import com.example.series_ranking.rating.entity.RatingId;
import com.example.series_ranking.rating.exception.RatingNotFoundException;
import com.example.series_ranking.rating.repository.RatingRepository;
import com.example.series_ranking.rating.repository.SeriesRepository;
import com.example.series_ranking.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RatingServiceImplTest {

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Mock
    private RatingRepository ratingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeriesRepository seriesRepository;

    @Mock
    private ModelMapper modelMapper;

    private RatingIdDTO ratingIdDTO;
    private Rating rating;
    private RatingId ratingId;
    private RatingDTO ratingDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ratingIdDTO = new RatingIdDTO(1L, 1L);
        ratingId = new RatingId(1L, 1L);
        rating = new Rating();
        rating.setId(ratingId);
        ratingDTO = new RatingDTO();
        ratingDTO.setId(ratingIdDTO);

    }

    @Test
    void testSaveRating() {
        when(userRepository.existsById(ratingIdDTO.getUserId())).thenReturn(true);
        when(seriesRepository.existsById(ratingIdDTO.getSeriesId())).thenReturn(true);
        when(ratingRepository.existsById(ratingId)).thenReturn(false);
        when(modelMapper.map(ratingDTO, Rating.class)).thenReturn(rating);
        when(ratingRepository.save(rating)).thenReturn(rating);
        when(modelMapper.map(rating, RatingDTO.class)).thenReturn(ratingDTO);

        RatingDTO result = ratingService.save(ratingDTO);

        assertNotNull(result);
        assertEquals(ratingDTO, result);
        verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    void testFindAll() {
        when(ratingRepository.findAll()).thenReturn(List.of(rating));
        when(modelMapper.map(rating, RatingDTO.class)).thenReturn(ratingDTO);

        List<RatingDTO> result = ratingService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(ratingDTO, result.get(0));
        verify(ratingRepository, times(1)).findAll();
    }

    @Test
    void testFindTopRatedSeries() {
        TopRatedSeriesResponseDTO topRatedSeriesResponseDTO = new TopRatedSeriesResponseDTO();
        when(ratingRepository.findTopRatedSeries()).thenReturn(Optional.of(List.of(topRatedSeriesResponseDTO)));

        Optional<List<TopRatedSeriesResponseDTO>> result = ratingService.findTopRatedSeries();

        assertTrue(result.isPresent());
        assertEquals(topRatedSeriesResponseDTO, result.get().get(0));
        verify(ratingRepository, times(1)).findTopRatedSeries();
    }

    @Test
    void testUpdateRatingNotFound() {
        when(userRepository.existsById(ratingIdDTO.getUserId())).thenReturn(true);
        when(seriesRepository.existsById(ratingIdDTO.getSeriesId())).thenReturn(true);
        when(ratingRepository.findById(ratingId)).thenReturn(Optional.empty());

        assertThrows(RatingNotFoundException.class, () -> ratingService.update(ratingDTO));
        verify(ratingRepository, never()).save(any(Rating.class));
    }
}
