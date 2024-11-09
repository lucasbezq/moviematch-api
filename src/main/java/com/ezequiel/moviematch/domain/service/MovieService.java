package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.domain.exception.NotFoundException;
import com.ezequiel.moviematch.domain.model.Movie;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static com.ezequiel.moviematch.domain.util.Constants.LIMIT_OF_RECOMMENDED_MOVIES;


@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private SearchGenreService searchGenreService;

    @Transactional
    public Movie add(Movie movie) {
        return repository.save(movie);
    }

    public Set<Movie> recommendMovies(String genreUuid) {
        var genre = searchGenreService.search(genreUuid);
        var movies = repository.findByGenresUuid(genre.getUuid());

        if (movies.isEmpty()) throw new NotFoundException("Nós não encontramos filmes para este gênero.");

        Collections.shuffle(movies);

        return movies.stream()
                .limit(LIMIT_OF_RECOMMENDED_MOVIES)
                .collect(Collectors.toSet());
    }

}
