package com.ezequiel.moviematch.builders;

import com.ezequiel.moviematch.domain.model.Movie;

import java.util.UUID;

public class MovieBuilder {

    private Movie movie;

    private MovieBuilder() {}

    public static MovieBuilder oneMovie() {
        var builder = new MovieBuilder();
        builder.movie = new Movie();
        builder.movie.setUuid(UUID.randomUUID().toString());
        builder.movie.setTitle("Um Sonho de Liberdade");
        builder.movie.setDuration("2 h 22 min");
        builder.movie.setRating(9.8);
        builder.movie.setSynopsis("Dois homens presos se reúnem ao longo de vários anos, encontrando consolo e eventual redenção através de atos de decência comum.");
        builder.movie.setImageUrl("https://i.postimg.cc/bwtGy1tf/shawshank-redemption.jpg");
        builder.movie.setReleaseYear(1998);
        return builder;
    }

    public MovieBuilder withTitle(String title) {
        movie.setTitle(title);
        return this;
    }

    public MovieBuilder withDuration(String duration) {
        movie.setDuration(duration);
        return this;
    }

    public MovieBuilder withRating(double rating) {
        movie.setRating(rating);
        return this;
    }

    public MovieBuilder withSynopsis(String synopsis) {
        movie.setSynopsis(synopsis);
        return this;
    }

    public MovieBuilder withImageUrl(String imageUrl) {
        movie.setImageUrl(imageUrl);
        return this;
    }

    public MovieBuilder withReleaseYear(int releaseYear) {
        movie.setReleaseYear(releaseYear);
        return this;
    }

    public Movie build() {
        return movie;
    }
}
