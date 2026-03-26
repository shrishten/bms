package com.example.bookmyshow.DTO;

import java.util.List;

import com.example.bookmyshow.entities.BookingStatus;

import lombok.Data;

@Data
public class BookingDTO {
    private String bookingTime;
    private BookingStatus status;
    private Float totalAmount;
    private Integer numberOfSeats;
    private List<String> seatNumbers;
    private Long userId;
    private Long showId;
}
