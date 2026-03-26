package com.example.bookmyshow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.bookmyshow.DTO.ShowDTO;
import com.example.bookmyshow.entities.Booking;
import com.example.bookmyshow.entities.Movie;
import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.repository.MovieRepository;
import com.example.bookmyshow.repository.ShowRepository;
import com.example.bookmyshow.repository.TheatreRepository;

@Service
public class ShowService {

    private ShowRepository showRepository;
    private MovieRepository movieRepository;
    private TheatreRepository theatreRepository;

    public Show createShow(ShowDTO showDTO) {

        // Assume this is autowired
        // Logic to create a new show
        Show show = new Show();
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + showDTO.getMovieId()));
        Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + showDTO.getTheatreId()));
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        showRepository.save(show);
        return show; // Placeholder return statement
    }

    public Show updateShow(Long id, ShowDTO showDTO) {
        // Logic to update an existing show
        Show show = showRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Show not found with id: " + id));
        Movie movie = movieRepository.findById(showDTO.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + showDTO.getMovieId()));
        Theatre theatre = theatreRepository.findById(showDTO.getTheatreId())
                .orElseThrow(() -> new RuntimeException("Theatre not found with id: " + showDTO.getTheatreId()));
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setShowTime(showDTO.getShowTime());
        show.setPrice(showDTO.getPrice());
        return showRepository.save(show);
    }

    public void deleteShow(Long id) {
        // Logic to delete a show
        if (!showRepository.findById(id).isPresent()) {
            throw new RuntimeException("Show not found with id: " + id);
        }
        
        List<Booking> bookings = showRepository.findById(id).get().getBookings();
        if (bookings.isEmpty()) {
            showRepository.deleteById(id);
        } else {
            throw new RuntimeException("Show has existing bookings and cannot be deleted.");
        }
    }

    public List<Show> getAllShows() {

        return showRepository.findAll();
    }

    public List<Show> getShowsByMovie(String movieName) {
        // Logic to get shows by movie
        
        Optional<List<Show>>shows = showRepository.findByMovieName(movieName);

        if(shows.isPresent()) {
            return shows.get();
        } else {
            throw new RuntimeException("No shows found for movie: " + movieName);
        }
    }

    public Optional<List<Show>> getShowsByTheatre(String theatreName) {
        // Logic to get shows by theatre
        Optional<List<Show>> shows = showRepository.findByTheatreName(theatreName);
        if (shows.isPresent()) {
            return shows;
        } else {
            throw new RuntimeException("No shows found for theatre: " + theatreName);
        }
    }

    public Optional<Show> findById(Long id) {
        return showRepository.findById(id);
    }
}

