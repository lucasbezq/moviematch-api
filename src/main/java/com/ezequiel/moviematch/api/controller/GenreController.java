package com.ezequiel.moviematch.api.controller;

import com.ezequiel.moviematch.api.converter.genre.GenreRecordConverter;
import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.domain.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/{genres}")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private GenreRecordConverter genreRecordConverter;

    @GetMapping
    public List<GenreRecord> findAll() {
        var genres = genreRepository.findAll();
        return genreRecordConverter.toCollectionRecord(genres);
    }

}
