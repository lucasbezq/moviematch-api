package com.ezequiel.moviematch.api.converter;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.api.record.genre.MovieRecord;
import com.ezequiel.moviematch.api.record.movie.MovieSummary;
import com.ezequiel.moviematch.domain.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieRecordConverter {

    @Autowired
    private GenreService genreService;

    public MovieRecord toMovieRecord(MovieSummary movieSummary) {
        var posterUrl = "https://image.tmdb.org/t/p/original" + movieSummary.poster_path();

        List<GenreRecord> genreNames = movieSummary.genre_ids().stream()
                .map(genreId -> new GenreRecord(genreId, genreService.getGenreNameById(genreId))) // Assumindo que getGenreNameById() retorna o nome do gênero
                .collect(Collectors.toList());

        return new MovieRecord(
                movieSummary.title(),
                movieSummary.overview(),
                movieSummary.release_date(),
                movieSummary.vote_average(),
                posterUrl,
                genreNames
        );
    }
}
