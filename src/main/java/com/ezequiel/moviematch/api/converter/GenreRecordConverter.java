package com.ezequiel.moviematch.api.converter;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.api.record.movie.MovieSummary;
import com.ezequiel.moviematch.domain.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreRecordConverter {

    @Autowired
    private GenreService genreService;

    public List<GenreRecord> toCollectionRecord(MovieSummary movieSummary) {
        return movieSummary.genre_ids().stream()
                .map(genreId -> new GenreRecord(genreId, genreService.getGenreNameById(genreId)))
                .collect(Collectors.toList());
    }
}
