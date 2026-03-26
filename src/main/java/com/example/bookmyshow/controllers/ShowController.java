package com.example.bookmyshow.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmyshow.DTO.ShowDTO;
import com.example.bookmyshow.entities.Show;
import com.example.bookmyshow.service.ShowService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/show")
public class ShowController {
    @Autowired
    private ShowService showService;

    @PostMapping("/createshow")
    public ResponseEntity<Show> createShow(ShowDTO showDTO) {
        // Logic to create a new show
        
        return ResponseEntity.ok(showService.createShow(showDTO));
    }
    @GetMapping("/getallshows")
    public ResponseEntity<List<Show>> getAllShows() {
        return ResponseEntity.ok(showService.getAllShows());
    }
    @GetMapping("/getshowsbymovie/{movie}")
    public ResponseEntity<List<Show>> getShowsByMovie(@RequestParam String movie) {
        return ResponseEntity.ok(showService.getShowsByMovie(movie));
    }
    @GetMapping("/getshowsbytheatre/{theatre}")
    public ResponseEntity<Optional<List<Show>>> getShowsByTheatre(@RequestParam String theatre) {
        return ResponseEntity.ok(showService.getShowsByTheatre(theatre));
    }
    @PutMapping("/updateshow/{id}")
    public ResponseEntity<Show> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
        return ResponseEntity.ok(showService.updateShow(id, showDTO));
    }
    @DeleteMapping("/deleteshow/{id}")
    public ResponseEntity<Void> deleteShow(@RequestParam Long id) {
        if(showService.findById(id).isPresent()) {
            showService.deleteShow(id);
        } else {    
            throw new RuntimeException("Show not found with id: " + id);
        }
        
        return ResponseEntity.ok().build();
    }
    
}
