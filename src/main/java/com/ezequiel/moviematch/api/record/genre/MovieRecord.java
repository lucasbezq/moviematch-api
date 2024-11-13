package com.ezequiel.moviematch.api.record.genre;

import java.util.List;

public record MovieRecord(String title,
                          String overview,
                          String releaseDate,
                          Double rating,
                          String poster,
                          List<GenreRecord> genres) {
}
