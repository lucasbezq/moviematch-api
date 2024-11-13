package com.ezequiel.moviematch.api.record.movie;

import java.util.List;

public record MovieSummary(
        String title,
        String overview,
        String release_date,
        double vote_average,
        String poster_path,
        List<Integer> genre_ids
) {}