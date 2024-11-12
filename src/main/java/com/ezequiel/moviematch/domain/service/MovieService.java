package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.api.gateway.TmdbGateway;
import com.ezequiel.moviematch.api.record.movie.MovieSummary;
import com.ezequiel.moviematch.domain.exception.BusinessException;
import com.ezequiel.moviematch.domain.exception.NotFoundException;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
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
    private MovieRepository repository;

    @Autowired
    private SearchGenreService searchGenreService;

    @Autowired
    private TmdbGateway gateway;

    @Value("${tmdb.api.authorization}")
    private String authorization;

    public List<MovieSummary> recommendMovies(String genreId, Double minVoteAverage) {
        try {
            var page = getRandomPage(genreId, minVoteAverage);

            var recommendedMovies = gateway.getPopularMovies(false, false,
                    DEFAULT_LANGUAGE, page, DEFAULT_POPULARITY_FILTER, minVoteAverage, genreId, authorization).results();

            if (recommendedMovies.isEmpty()) throw new NotFoundException("Nós não encontramos filmes para este gênero.");

            Collections.shuffle(recommendedMovies);

            return recommendedMovies.stream()
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
                DEFAULT_PAGE, DEFAULT_POPULARITY_FILTER, minVoteAverage, genreId, authorization).total_pages();
        return (int) (Math.random() * totalPages) + 1;
    }
}
