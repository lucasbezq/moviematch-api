package com.ezequiel.moviematch.builders;

import com.ezequiel.moviematch.domain.model.Genre;

import java.util.UUID;

public class GenreBuilder {

    private Genre genre;

    private GenreBuilder() {}

    public static GenreBuilder builder() {
        var builder = new GenreBuilder();
        builder.genre = new Genre();
        builder.genre.setUuid(UUID.randomUUID().toString());
        return builder;
    }

    public GenreBuilder withId(Long id) {
        genre.setId(id);
        return this;
    }

    public GenreBuilder withName(String name) {
        genre.setName(name);
        return this;
    }

    public Genre build() {
        return genre;
    }

}
