package com.ezequiel.moviematch.api.record.movie;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;

import java.util.List;

public record MovieRecord(String title,
                          String overview,
                          String releaseDate,
                          String rating,
                          String poster,
                          List<GenreRecord> genres) {
}
