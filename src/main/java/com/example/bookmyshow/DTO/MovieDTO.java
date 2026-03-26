package com.example.bookmyshow.DTO;

import java.time.LocalDate;

import lombok.Data;


@Data
public class MovieDTO {
    private String name;
    private String genre;
    private String language;
    private Integer duration;
    private LocalDate releaseDate;
    private String description;
}
