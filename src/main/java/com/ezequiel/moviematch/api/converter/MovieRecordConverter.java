package com.ezequiel.moviematch.api.converter;

import com.ezequiel.moviematch.api.record.movie.MovieRecord;
import com.ezequiel.moviematch.api.record.movie.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.ezequiel.moviematch.domain.util.Constants.PATH_TMDB_IMAGE_REPOSITORY;

@Component
public class MovieRecordConverter {

    @Autowired
    private GenreRecordConverter genreRecordConverter;

    public MovieRecord toMovieRecord(MovieSummary movieSummary) {
        var posterUrl = PATH_TMDB_IMAGE_REPOSITORY.concat(movieSummary.poster_path());
        var genreNames = genreRecordConverter.toCollectionRecord(movieSummary);

        return new MovieRecord(
                movieSummary.title(),
                movieSummary.overview(),
                movieSummary.release_date(),
                String.format("%.1f", movieSummary.vote_average()),
                posterUrl,
                genreNames
        );
    }
}
