package com.example.bookmyshow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmyshow.DTO.MovieDTO;
import com.example.bookmyshow.entities.Movie;
import com.example.bookmyshow.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(MovieDTO movieDTO) {
        // Logic to convert MovieDTO to Movie entity and save it
        // For simplicity, let's assume we have a method to convert DTO to Entity
        Movie movie = new Movie();
        movie.setName(movieDTO.getName());
        movie.setGenre(movieDTO.getGenre());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setDescription(movieDTO.getDescription());

        return movieRepository.save(movie);

    }

    public List<Movie> getAllMovies() {
        // Logic to retrieve all movies
        return movieRepository.findAll();
    }

    public List<Movie> getMoviesByGenre(String genre) {
        // Logic to retrieve movies by genre
        Optional<List<Movie>> listOfMovieBox = movieRepository.findByGenre(genre);
        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else
            throw new RuntimeException("No movies found for genre: " + genre);
    }

    public List<Movie> getMoviesByLanguage(String language) {
        // Logic to retrieve movies by language
        Optional<List<Movie>> listOfMovieBox = movieRepository.findByLanguage(language);
        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else
            throw new RuntimeException("No movies found for genre: " + language);
    }

    public List<Movie> getMoviesByTitle(String title) {
        // Logic to retrieve movies by title
        Optional<List<Movie>> listOfMovieBox = movieRepository.findByNameContainingIgnoreCase(title);
        if (listOfMovieBox.isPresent()) {
            return listOfMovieBox.get();
        } else
            throw new RuntimeException("No movies found for title: " + title);
    }

    public Movie updateMovie(Long id, MovieDTO movieDTO) {
        // Logic to update a movie
        Optional<Movie> movieBox = movieRepository.findById(id);
        if (movieBox.isPresent()) {
            Movie movie = movieBox.get();
            movie.setName(movieDTO.getName());
            movie.setGenre(movieDTO.getGenre());
            movie.setLanguage(movieDTO.getLanguage());
            movie.setDuration(movieDTO.getDuration());
            movie.setReleaseDate(movieDTO.getReleaseDate());
            movie.setDescription(movieDTO.getDescription());

            return movieRepository.save(movie);
        } else
            throw new RuntimeException("Movie not found with id: " + id);
    }

    public void deleteMovie(Long id) {
        // Logic to delete a movie
        if (movieRepository.existsById(id)) {
            movieRepository.deleteById(id);
        } else
            throw new RuntimeException("Movie not found with id: " + id);
    }
}
