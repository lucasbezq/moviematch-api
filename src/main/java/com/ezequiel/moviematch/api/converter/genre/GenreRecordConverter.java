package com.ezequiel.moviematch.api.converter.genre;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.domain.model.Genre;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreRecordConverter {

    public GenreRecord toRecord(Genre genre) {
        return new GenreRecord(genre.getUuid(), genre.getName());
    }

    public List<GenreRecord> toCollectionRecord(Collection<Genre> genres) {
        return genres.stream()
                .map(this::toRecord)
                .collect(Collectors.toList());
    }
}
