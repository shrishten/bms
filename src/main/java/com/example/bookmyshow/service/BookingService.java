package com.example.bookmyshow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmyshow.DTO.BookingDTO;
import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.BookingStatus;
import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.entities.User;
import com.example.bookmyshow.repository.BookingRepository;
import com.example.bookmyshow.repository.ShowRepository;
import com.example.bookmyshow.repository.UserRepository;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private UserRepository userRepository;

    public Booking createBooking(BookingDTO bookingDTO) {

        Show show = showRepository.findById(bookingDTO.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + bookingDTO.getShowId()));
        Optional<User> userOptional = userRepository.findById(bookingDTO.getUserId());
        User user = userOptional.get();
        if (!isSeatsAvailable(show.getId(), bookingDTO.getNumberOfSeats())) {
            throw new RuntimeException("Not enough seats available for show id: " + bookingDTO.getShowId());
        }
        if (bookingDTO.getSeatNumbers().size() != bookingDTO.getNumberOfSeats()) {
            throw new RuntimeException("Number of seat numbers provided does not match the number of seats requested.");
        }
        validateDuplicateSeats(show.getId(), bookingDTO.getSeatNumbers());

        Booking booking = new Booking();
        booking.setShow(show);
        booking.setUser(user);
        booking.setBookingTime(LocalDateTime.now());
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setTotalAmount(calculateTotalAmount(show.getPrice(), bookingDTO.getNumberOfSeats()));
        booking.setNumberOfSeats(bookingDTO.getNumberOfSeats());
        booking.setSeatNumbers(bookingDTO.getSeatNumbers());

        return bookingRepository.save(booking);
        // Logic to create a booking
    }

    private Double calculateTotalAmount(Double price, Integer numberOfSeats) {
        // TODO Auto-generated method stub
        return price * numberOfSeats;
    }

    public boolean isSeatsAvailable(Long showId, int numberOfSeats) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));
        int bookedSeats = show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                .mapToInt(Booking::getNumberOfSeats)
                .sum();
        return (show.getTheatre().getTheatreCapacity() - bookedSeats) >= numberOfSeats;
    }

    public void validateDuplicateSeats(Long showId, java.util.List<String> seatNumbers) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + showId));
        Set<String> bookedSeats = show.getBookings().stream()
                .filter(booking -> booking.getBookingStatus() != BookingStatus.CANCELLED)
                .flatMap(booking -> booking.getSeatNumbers().stream())
                .collect(Collectors.toSet());
        List<String> duplicateSeats = seatNumbers.stream()
                .filter(bookedSeats::contains)
                .collect(Collectors.toList());
        if (!duplicateSeats.isEmpty()) {
            throw new RuntimeException("The following seats are already booked: " + duplicateSeats);
        }

    }

    public List<Booking> getUserBookings(Long userId) {
        // Logic to retrieve bookings for a specific user
        return bookingRepository.findByUserId(userId);

    }

    public List<Booking> getShowBookings(Long showId) {
        // Logic to retrieve bookings for a specific show
        return bookingRepository.findByShowId(showId);
    }

    public Booking confirmBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Only pending bookings can be confirmed.");
        }

        // payment api process
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        validateCancellation(booking);
        // refund api process
        booking.setBookingStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    private void validateCancellation(Booking booking) {
        // TODO Auto-generated method stub
        LocalDateTime showTime = booking.getShow().getShowTime();
        LocalDateTime deadlineTime = showTime.minusHours(2);
        if (LocalDateTime.now().isAfter(deadlineTime)) {
            throw new RuntimeException("Bookings can only be cancelled up to 2 hours before the showtime.");
        }
        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled.");
        }
    }

    public List<Booking> getBookingsByStatus(BookingStatus bookingStatus) {
        // Logic to retrieve bookings by status
        return bookingRepository.findByBookingStatus(bookingStatus);
    }

}
