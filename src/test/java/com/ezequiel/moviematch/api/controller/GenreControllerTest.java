package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.converter.genre.GenreRecordConverter;
import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.builders.GenreBuilder;
import com.ezequiel.moviematch.domain.model.Genre;
import com.ezequiel.moviematch.domain.repository.GenreRepository;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreRepository repository;

    @MockBean
    private GenreRecordConverter genreRecordConverter;

    private List<Genre> genres;
    private List<GenreRecord> genreRecords;

    @BeforeEach
    void setup() {
        var genre1 = GenreBuilder.builder().withId(1L).withName("Comédia").build();
        var genre2= GenreBuilder.builder().withId(2L).withName("Crime").build();
        genres = Arrays.asList(genre1, genre2);

        var genreRecord1 = new GenreRecord("1", "Comédia");
        var genreRecord2 = new GenreRecord("2", "Crime");
        genreRecords = Arrays.asList(genreRecord1, genreRecord2);
    }

    @Test
    @DisplayName("Should return all genres successfully")
    void shouldReturnAllGenresSuccessfully() throws Exception {
        when(repository.findAll()).thenReturn(genres);
        when(genreRecordConverter.toCollectionRecord(genres)).thenReturn(genreRecords);

        mockMvc.perform(get("/genres")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Comédia")))
                .andExpect(jsonPath("$[1].name", is("Crime")));
    }

}
