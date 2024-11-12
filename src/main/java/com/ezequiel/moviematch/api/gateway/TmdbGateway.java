package com.ezequiel.moviematch.api.gateway;

import com.ezequiel.moviematch.api.record.genre.GenreResponseRecord;
import com.ezequiel.moviematch.api.record.movie.MovieResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tmdb-gateway", url = "${tmdb.api.url}")
public interface TmdbGateway {

    @GetMapping("/genre/movie/list")
    GenreResponseRecord getGenres(@RequestParam String language, @RequestHeader("Authorization") String authorization);

    @GetMapping("/discover/movie")
    MovieResponse getPopularMovies(
            @RequestParam(value = "include_adult", defaultValue = "false") boolean includeAdult,
            @RequestParam(value = "include_video", defaultValue = "false") boolean includeVideo,
            @RequestParam(value = "language") String language,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "sort_by", defaultValue = "popularity.desc") String sortBy,
            @RequestParam(value = "vote_average.gte", defaultValue = "8") double minVoteAverage,
            @RequestParam(value = "with_genres", defaultValue = "16") String genreId,
            @RequestHeader("Authorization") String authorization
    );

}
