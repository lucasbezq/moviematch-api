package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.record.movie.MovieResponse;
import com.ezequiel.moviematch.domain.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public MovieResponse getMovies(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam String genreId) {
        return movieService.getPopularMovies(page, genreId);
    }

}
