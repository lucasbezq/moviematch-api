package com.ezequiel.moviematch.api.converter;

import com.ezequiel.moviematch.api.record.request.MovieRequest;
import com.ezequiel.moviematch.domain.model.Movie;
import com.ezequiel.moviematch.domain.service.SearchGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieConverter {

    @Autowired
    private SearchGenreService searchGenreService;

    public Movie toModel(MovieRequest movieRequest) {
        var model = new Movie();
        var genres = movieRequest.genres()
                .stream()
                .map(genre -> searchGenreService.search(genre.uuid()))
                .collect(Collectors.toSet());

        model.setTitle(movieRequest.title());
        model.setDuration(movieRequest.duration());
        model.setSynopsis(movieRequest.synopsis());
        model.setReleaseYear(movieRequest.releaseYear());
        model.setImageUrl(movieRequest.imageUrl());
        model.setGenres(genres);

        return model;
    }

}
