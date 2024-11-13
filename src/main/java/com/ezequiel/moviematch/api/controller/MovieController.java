package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.record.genre.MovieRecord;
import com.ezequiel.moviematch.domain.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieRecord> getRecommendedMovies(@RequestParam String genreId,
                                                  @RequestParam(defaultValue = "8") Double minVoteAverage) {
        return movieService.recommendMovies(genreId, minVoteAverage);
    }

}
