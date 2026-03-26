package com.example.bookmyshow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.example.bookmyshow.entities.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    Optional<List<Show>> findByMovieName(String movieName);
    Optional<List<Show>> findByTheatreName(String theatreName);

}
