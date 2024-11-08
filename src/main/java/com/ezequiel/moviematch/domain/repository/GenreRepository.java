package com.ezequiel.moviematch.domain.repository;

import com.ezequiel.moviematch.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByUuid(String uuid);

}
