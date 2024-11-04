package com.example.series_ranking.rating.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SeriesDTO {

    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Platform cannot be blank")
    @Size(max = 100, message = "Platform must be at most 100 characters")
    private String platform;

    @NotBlank(message = "Synopsis cannot be blank")
    @Size(max = 1000, message = "Synopsis must be at most 1000 characters")
    private String synopsis;

    @NotBlank(message = "Cover cannot be blank")
    @Size(max = 255, message = "Cover URL must be at most 255 characters")
    private String cover;
}
