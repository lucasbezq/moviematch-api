package com.ezequiel.moviematch.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends BusinessException {

    private static final long serialVersionUID = 8760185720984836235L;

    public NotFoundException(String mensagem) {
        super(mensagem);
    }

}
