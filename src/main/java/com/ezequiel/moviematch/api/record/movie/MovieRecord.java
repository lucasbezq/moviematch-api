package com.ezequiel.moviematch.api.record.movie;

import com.ezequiel.moviematch.api.record.genre.GenreRecord;

import java.util.Set;

public record MovieRecord(String uuid, String title, String duration, Double rating, String synopsis,
                          Integer releaseYear, String imageUrl, Set<GenreRecord> genres) {
}
