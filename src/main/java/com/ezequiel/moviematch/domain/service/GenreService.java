package com.ezequiel.moviematch.domain.service;

import com.ezequiel.moviematch.api.gateway.TmdbGateway;
import com.ezequiel.moviematch.api.record.genre.GenreRecord;
import com.ezequiel.moviematch.domain.exception.BusinessException;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private TmdbGateway gateway;

    @Value("${tmdb.api.authorization}")
    private String authorization;

    public List<GenreRecord> getGenres() {
        try {
            return gateway.getGenres("pt-BR", authorization).genres();
        } catch (FeignException e) {
            throw new BusinessException("Erro ao acessar a API externa de gêneros.");
        } catch (Exception e) {
            throw new BusinessException("Erro interno.");
        }
    }
}
