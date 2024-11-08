package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.domain.exception.NotFoundException;
import com.ezequiel.moviematch.domain.model.Genre;
import com.ezequiel.moviematch.domain.model.Movie;
import com.ezequiel.moviematch.domain.repository.GenreRepository;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchGenreService {

    @Autowired
    private GenreRepository genreRepository;

    public Genre search(String uuid) {
        return genreRepository.findByUuid(uuid)
                .orElseThrow(() -> new NotFoundException("Genre not found."));
    }

}
