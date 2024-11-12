package com.ezequiel.moviematch.api.record.movie;

import java.util.List;

public record MovieResponse(
        int page,
        List<MovieSummary> results,
        int total_pages,
        int total_results
) {}
