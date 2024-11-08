package com.ezequiel.moviematch.api.record.request;

import jakarta.validation.constraints.NotBlank;

public record GenreRequest(@NotBlank String uuid) {
}
