package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.converter.movie.MovieConverter;
import com.ezequiel.moviematch.api.converter.movie.MovieRecordConverter;
import com.ezequiel.moviematch.api.converter.movie.MovieSummaryRecordConverter;
import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.api.record.movie.MovieRecord;
import com.ezequiel.moviematch.api.record.movie.MovieSummaryRecord;
import com.ezequiel.moviematch.domain.model.Movie;
import com.ezequiel.moviematch.domain.repository.MovieRepository;
import com.ezequiel.moviematch.domain.service.MovieService;
import com.ezequiel.moviematch.domain.service.SearchMovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.ezequiel.moviematch.builders.MovieBuilder.oneMovie;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MovieRepository repository;

    @MockBean
    private MovieSummaryRecordConverter movieSummaryRecordConverter;

    @MockBean
    private MovieRecordConverter movieRecordConverter;

    @MockBean
    private MovieConverter movieConverter;

    @MockBean
    private SearchMovieService searchMovieService;

    @MockBean
    private MovieService movieService;

    private List<Movie> movies;
    private List<MovieSummaryRecord> moviesSummaryRecord;
    private MovieRecord movieRecord;
    private Set<GenreRecord> genres;

    @BeforeEach
    void setup() {
        var movie1 = oneMovie().build();

        var movie2 = oneMovie()
                .withTitle("A Lista de Schindler")
                .withDuration("3h 15min")
                .withRating(9.0)
                .withSynopsis("Durante a Segunda Guerra Mundial, um empresário salva centenas de vidas judias.")
                .withImageUrl("https://i.postimg.cc/J4hZLyxR/schindler-list.jpg")
                .withReleaseYear(1993)
                .build();

        movies = Arrays.asList(movie1, movie2);

        var moviesSummaryRecord1 = new MovieSummaryRecord("1",
                "Um Sonho de Liberdade",
                "2 h 22 min",
                9.8,
                "https://i.postimg.cc/bwtGy1tf/shawshank-redemption.jpg"
        );

        var moviesSummaryRecord2 = new MovieSummaryRecord("2",
                "A Lista de Schindler",
                "3h 15min",
                9.0,
                "https://i.postimg.cc/J4hZLyxR/schindler-list.jpg"
        );

        moviesSummaryRecord = Arrays.asList(moviesSummaryRecord1, moviesSummaryRecord2);

        genres = new HashSet<>(List.of(new GenreRecord("1", "Drama")));

        movieRecord = new MovieRecord(
                "128f4544-08bc-4716-9ccd-4d712102b575",
                "A Lista de Schindler",
                "3h 15min",
                9.0,
                "Durante a Segunda Guerra Mundial, um empresário salva centenas de vidas judias.",
                1993,
                "https://i.postimg.cc/J4hZLyxR/schindler-list.jpg",
                genres
        );
    }

    @Test
    @DisplayName("Should return all movies successfully")
    void shouldReturnAllMoviesSuccessfully() throws Exception {
        when(repository.findAll()).thenReturn(movies);
        when(movieSummaryRecordConverter.toCollectionRecord(movies)).thenReturn(moviesSummaryRecord);

        mockMvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Um Sonho de Liberdade")))
                .andExpect(jsonPath("$[1].title", is("A Lista de Schindler")));

    }

    @Test
    @DisplayName("Should return the correct movie when fetched by UUID")
    void shouldReturnCorrectMovieWhenSearchedByUuid() throws Exception {
        var movie = movies.getFirst();
        movie.setUuid("128f4544-08bc-4716-9ccd-4d712102b575");
        var movieUuid = movie.getUuid();

        when(searchMovieService.search(movieUuid)).thenReturn(movie);
        when(movieRecordConverter.toRecord(movie)).thenReturn(movieRecord);

        mockMvc.perform(get("/movies/{movieUuid}", movieUuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("A Lista de Schindler")));
    }

}
