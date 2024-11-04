package com.example.series_ranking.rating.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class RatingId implements Serializable {

    private Long seriesId;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingId ratingId = (RatingId) o;
        return Objects.equals(seriesId, ratingId.seriesId) &&
                Objects.equals(userId, ratingId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seriesId, userId);
    }
}
