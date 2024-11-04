package com.example.series_ranking.rating.repository;

import com.example.series_ranking.rating.dto.TopRatedSeriesResponseDTO;
import com.example.series_ranking.rating.entity.Rating;
import com.example.series_ranking.rating.entity.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {

    @Query("SELECT new com.example.series_ranking.rating.dto.TopRatedSeriesResponseDTO(s.cover, s.name, s.platform, s.synopsis, AVG(r.rating)) " +
            "FROM Rating r JOIN r.series s " +
            "GROUP BY s.id " +
            "ORDER BY AVG(r.rating) DESC")
    Optional<List<TopRatedSeriesResponseDTO>> findTopRatedSeries();

}