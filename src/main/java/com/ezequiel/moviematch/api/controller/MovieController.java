package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.converter.MovieRecordConverter;
import com.ezequiel.moviematch.api.converter.MovieSummaryRecordConverter;
import com.ezequiel.moviematch.api.record.movie.MovieRecord;
import com.ezequiel.moviematch.api.record.movie.MovieSummaryRecord;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import com.ezequiel.moviematch.domain.service.SearchMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieSummaryRecordConverter movieSummaryRecordConverter;

    @Autowired
    private MovieRecordConverter movieRecordConverter;

    @Autowired
    private SearchMovieService searchMovieService;

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

}
