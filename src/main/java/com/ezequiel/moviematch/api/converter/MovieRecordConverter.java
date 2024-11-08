package com.ezequiel.moviematch.api.converter;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.api.record.movie.MovieRecord;
import com.ezequiel.moviematch.domain.model.Movie;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MovieRecordConverter {



    public MovieRecord toRecord(Movie movie) {

        var genreRecords = movie.getGenres().stream()
                .map(genre -> new GenreRecord(genre.getUuid(), genre.getName()))
                .collect(Collectors.toSet());

        return new MovieRecord(
                movie.getUuid(),
                movie.getTitle(),
                movie.getDuration(),
                movie.getRating(),
                movie.getSynopsis(),
                movie.getReleaseYear(),
                movie.getImageUrl(),
                genreRecords
        );
    }

}
