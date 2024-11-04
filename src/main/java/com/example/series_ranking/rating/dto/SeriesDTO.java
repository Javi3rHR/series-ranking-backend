package com.example.series_ranking.rating.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeriesDTO {

    private Long id;

    @NotNull(message = "Name cannot be null")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @Size(max = 100, message = "Platform must be at most 100 characters")
    private String platform;

    @Size(max = 1000, message = "Synopsis must be at most 1000 characters")
    private String synopsis;

    @Size(max = 255, message = "Cover URL must be at most 255 characters")
    private String cover;
}
