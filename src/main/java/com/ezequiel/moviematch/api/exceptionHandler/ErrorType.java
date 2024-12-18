package com.ezequiel.moviematch.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ErrorType {

    INVALID_DATA("mensagem-incompreensivel", "Mensagem incompreensível."),
    RESOURCE_NOT_FOUND("recurso-nao-encontrado", "Recurso não encontrado."),
    INTERNAL_ERROR("problema-interno", "Ocorreu um erro inesperado. Tente novamente mais tarde."),;

    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.uri = "https://moviematch.com.br/" + path;
        this.title = title;
    }
}

