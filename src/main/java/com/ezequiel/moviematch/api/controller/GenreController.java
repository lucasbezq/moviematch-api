package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.domain.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService service;

    @GetMapping("/movies")
    public List<GenreRecord> getMovieGenres() {
        return service.getGenres();
    }

}
