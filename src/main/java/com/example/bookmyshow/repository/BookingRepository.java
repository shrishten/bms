package com.example.bookmyshow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.BookingStatus;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);

    List<Booking> findByShowId(Long showId);

    List<Booking> findByBookingStatus(BookingStatus bookingStatus);
}
