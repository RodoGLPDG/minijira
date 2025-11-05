package com.portafolio.minijira.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException(String email){
        super("El email ya esta en uso" + email);
    }

}
