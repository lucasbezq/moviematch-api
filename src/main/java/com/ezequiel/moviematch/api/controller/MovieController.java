package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.converter.movie.MovieConverter;
import com.ezequiel.moviematch.api.converter.movie.MovieRecordConverter;
import com.ezequiel.moviematch.api.converter.movie.MovieSummaryRecordConverter;
import com.ezequiel.moviematch.api.record.movie.MovieRecord;
import com.ezequiel.moviematch.api.record.movie.MovieSummaryRecord;
import com.ezequiel.moviematch.api.record.request.MovieRequest;
import com.ezequiel.moviematch.domain.repository.GenreRepository;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import com.ezequiel.moviematch.domain.service.MovieService;
import com.ezequiel.moviematch.domain.service.SearchMovieService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieSummaryRecordConverter movieSummaryRecordConverter;

    @Autowired
    private MovieRecordConverter movieRecordConverter;

    @Autowired
    private MovieConverter movieConverter;

    @Autowired
    private SearchMovieService searchMovieService;

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieSummaryRecord> findAll() {
        var movies = movieRepository.findAll();
        return movieSummaryRecordConverter.toCollectionRecord(movies);
    }

    @GetMapping("/{movieUuid}")
    public MovieRecord findMovie(@PathVariable String movieUuid) {
        var movie = searchMovieService.search(movieUuid);
        return movieRecordConverter.toRecord(movie);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieRecord add(@RequestBody @Valid MovieRequest movieRequest) {
        var movie = movieConverter.toModel(movieRequest);
        var newMovie = movieService.add(movie);
        return movieRecordConverter.toRecord(newMovie);
    }

}
