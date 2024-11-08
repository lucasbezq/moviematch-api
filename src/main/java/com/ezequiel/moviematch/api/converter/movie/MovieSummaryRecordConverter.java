package com.ezequiel.moviematch.api.converter.movie;

import com.ezequiel.moviematch.api.record.movie.MovieSummaryRecord;
import com.ezequiel.moviematch.domain.model.Movie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieSummaryRecordConverter {

    public MovieSummaryRecord toRecord(Movie movie) {
        return new MovieSummaryRecord(
                movie.getUuid(),
                movie.getTitle(),
                movie.getDuration(),
                movie.getRating(),
                movie.getImageUrl()
        );
    }

    public List<MovieSummaryRecord> toCollectionRecord(List<Movie> movies) {
        return movies.stream()
                .map(this::toRecord)
                .collect(Collectors.toList());
    }

}
