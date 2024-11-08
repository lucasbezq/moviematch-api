package com.ezequiel.moviematch.api.record.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record MovieRequest(@NotBlank String title,
                           @NotBlank String duration,
                           @NotBlank String rating,
                           @NotBlank String imageUrl,
                           @NotBlank String synopsis,
                           @NotNull Integer releaseYear,
                           @NotNull @Valid Set<GenreRequest> genres) {
}
