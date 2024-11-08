package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.domain.model.Movie;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Transactional
    public Movie add(Movie movie) {
        return repository.save(movie);
    }

}
