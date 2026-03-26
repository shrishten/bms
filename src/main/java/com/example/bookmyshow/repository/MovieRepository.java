package com.example.bookmyshow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.bookmyshow.entities.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    public Optional<List<Movie>> findByGenre(String genre);

    public Optional<List<Movie>> findByLanguage(String language);

    public Optional<List<Movie>> findByNameContainingIgnoreCase(String title);
    
}
