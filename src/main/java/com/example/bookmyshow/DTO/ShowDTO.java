package com.example.bookmyshow.DTO;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShowDTO {
    
    private Long movieId;
    private Long theatreId;
    private LocalDateTime showTime;
    private Double price;

}
