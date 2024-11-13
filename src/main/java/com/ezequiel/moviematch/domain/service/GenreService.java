package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.api.gateway.TmdbGateway;
import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.domain.exception.BusinessException;
import feign.FeignException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GenreService {

    @Autowired
    private TmdbGateway gateway;

    @Value("${tmdb.api.authorization}")
    private String authorization;

    private final Map<Integer, String> genreMap = new HashMap<>();

    @PostConstruct
    public void initializer() {
        this.genreMap.putAll(loadGenres());
    }


    public List<GenreRecord> getGenres() {
        try {
            return gateway.getGenres("pt-BR", authorization).genres();
        } catch (FeignException e) {
            throw new BusinessException("Erro ao acessar a API externa de gêneros.");
        } catch (Exception e) {
            throw new BusinessException("Erro interno.");
        }
    }

    private Map<Integer, String> loadGenres() {
        var genres = gateway.getGenres("pt-BR", authorization).genres();
        return genres.stream().collect(Collectors.toMap(GenreRecord::id, GenreRecord::name));
    }

    public String getGenreNameById(Integer genreId) {
        return genreMap.getOrDefault(genreId, "Unknown Genre");
    }
}
