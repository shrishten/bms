package com.example.bookmyshow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmyshow.DTO.TheatreDTO;
import com.example.bookmyshow.entities.Theatre;
import com.example.bookmyshow.service.TheatreService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/theatre")
public class TheatreController {
    
    @Autowired
    private TheatreService theatreService;

    @PostMapping("/addtheatre")
    public ResponseEntity<Theatre> addTheatre(@RequestBody TheatreDTO theatreDTO) {
        // Logic to add a new theatre
        return ResponseEntity.ok(theatreService.addTheatre(theatreDTO));
    }
    @GetMapping("/gettheatrebylocation")
    public ResponseEntity<List<Theatre>> getTheatresByLocation(@RequestParam String location) {
        // Logic to get all theatres
        return ResponseEntity.ok(theatreService.getTheatresByLocation(location));
    }

    @PutMapping("/updatetheatre/{id}")
    public ResponseEntity<Theatre> updateTheatre(@RequestParam Long id, @RequestBody TheatreDTO theatreDTO) {
        // Logic to get all theatres
        return ResponseEntity.ok(theatreService.updateTheatre(id, theatreDTO));
    }

    @DeleteMapping("/deletetheatre/{id}")
    public ResponseEntity<Void> deleteTheatre(@RequestParam Long id) {
        // Logic to get all theatres
        theatreService.deleteTheatre(id);
        return ResponseEntity.ok().build();
    }
    
}
