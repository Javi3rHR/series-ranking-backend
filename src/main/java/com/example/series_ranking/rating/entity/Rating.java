package com.example.series_ranking.rating.entity;

import com.example.series_ranking.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rating")
@Data
@NoArgsConstructor
public class Rating {

    @EmbeddedId
    private RatingId id;

    @Column(nullable = false)
    private Double rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("seriesId")
    @JoinColumn(name = "series_id", nullable = false)
    private Series series;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
