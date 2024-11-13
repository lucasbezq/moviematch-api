package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.api.converter.MovieRecordConverter;
import com.ezequiel.moviematch.api.gateway.TmdbGateway;
import com.ezequiel.moviematch.api.record.movie.MovieRecord;
import com.ezequiel.moviematch.domain.exception.BusinessException;
import com.ezequiel.moviematch.domain.exception.NotFoundException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.ezequiel.moviematch.domain.util.Constants.*;

@Service
public class MovieService {

    @Autowired
    private TmdbGateway gateway;

    @Autowired
    private MovieRecordConverter movieRecordConverter;

    @Value("${tmdb.api.authorization}")
    private String authorization;

    public List<MovieRecord> recommendMovies(String genreId, Double minVoteAverage) {
        try {
            var page = getRandomPage(genreId, minVoteAverage);

            var recommendedMovies = gateway.getPopularMovies(false, false,
                    DEFAULT_LANGUAGE, page, DEFAULT_POPULARITY_FILTER, minVoteAverage, genreId, DEFAULT_REGION, authorization).results();

            if (recommendedMovies.isEmpty()) throw new NotFoundException("Nós não encontramos filmes para este gênero.");

            Collections.shuffle(recommendedMovies);

            return recommendedMovies.stream()
                    .map(movieSummary -> movieRecordConverter.toMovieRecord(movieSummary))
                    .limit(LIMIT_OF_RECOMMENDED_MOVIES)
                    .collect(Collectors.toList());

        } catch (FeignException e) {
            throw new BusinessException("Erro ao acessar a API externa de gêneros.");
        } catch (Exception e) {
            throw new BusinessException("Erro interno.");
        }
    }

    public Integer getRandomPage(String genreId, Double minVoteAverage) {
        var totalPages = gateway.getPopularMovies(false, false, DEFAULT_LANGUAGE,
                DEFAULT_PAGE, DEFAULT_POPULARITY_FILTER, minVoteAverage, genreId, DEFAULT_REGION, authorization).total_pages();

        var page = Math.min(totalPages, 10);
        return (int) (Math.random() * page) + 1;
    }
}
