package com.ezequiel.moviematch.domain.exception;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3519388379314589374L;

    public BusinessException(String mensagem) {
        super(mensagem);
    }

    public BusinessException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
