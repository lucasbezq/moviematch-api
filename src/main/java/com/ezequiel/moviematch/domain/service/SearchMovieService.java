package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.domain.model.Movie;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchMovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie search(String uuid) {
        return movieRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Movie not found."));
    }

}
