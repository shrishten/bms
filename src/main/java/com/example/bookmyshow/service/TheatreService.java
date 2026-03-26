package com.example.bookmyshow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmyshow.DTO.TheatreDTO;
import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.repository.TheatreRepository;

@Service
public class TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;

    public Theatre addTheatre(TheatreDTO theatreDTO) {
        // Logic to add a new theatre
        Theatre theatre = new Theatre();
        theatre.setName(theatreDTO.getTheatreName());
        theatre.setTheatreLocation(theatreDTO.getTheatreLocation());
        theatre.setTheatreCapacity(theatreDTO.getTheatreCapacity());
        theatre.setTheatreScreenType(theatreDTO.getTheatreScreenType());
        return theatreRepository.save(theatre);
    }

    public List<Theatre> getTheatresByLocation(String location) {
        // Logic to get all theatres
        Optional<List<Theatre>> listOfTheatreBox = theatreRepository.findByTheatreLocation(location);
        if (listOfTheatreBox.isPresent()) {
            return listOfTheatreBox.get();
        } else
            throw new RuntimeException("No theatres found for location: " + location);
    }

    public Theatre updateTheatre(Long id, TheatreDTO theatreDTO) {
        // Logic to get all theatres
        Optional<Theatre> theatreBox = theatreRepository.findById(id);
        if (theatreBox.isPresent()) {
            Theatre theatre = theatreBox.get();
            theatre.setName(theatreDTO.getTheatreName());
            theatre.setTheatreLocation(theatreDTO.getTheatreLocation());
            theatre.setTheatreCapacity(theatreDTO.getTheatreCapacity());
            theatre.setTheatreScreenType(theatreDTO.getTheatreScreenType());
            return theatreRepository.save(theatre);
        } else {
            throw new RuntimeException("Theatre not found with id: " + id);
        }
    }

    public void deleteTheatre(Long id) {
        // Logic to delete a theatre
        if (theatreRepository.existsById(id)) {
            theatreRepository.deleteById(id);
        } else
            throw new RuntimeException("Theatre not found with id: " + id);
    }

}