package com.ezequiel.moviematch.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Movie {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uuid;
    private String title;
    private String duration;
    private Double rating;
    private String synopsis;
    private String imageUrl;
    private Integer releaseYear;

    @ManyToMany
    @JoinTable(name = "movie_genre", joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @PrePersist
    private void generateUuid() {
        setUuid(UUID.randomUUID().toString());
    }

    public Movie(String title, String duration, Double rating, String synopsis, String imageUrl, Integer releaseYear) {
        this.title = title;
        this.duration = duration;
        this.rating = rating;
        this.synopsis = synopsis;
        this.releaseYear = releaseYear;
        this.imageUrl = imageUrl;
    }

    public Movie() {}

}
