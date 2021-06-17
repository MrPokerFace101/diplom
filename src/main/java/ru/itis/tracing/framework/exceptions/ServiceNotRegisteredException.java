package ru.itis.tracing.framework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceNotRegisteredException extends RuntimeException{

    public ServiceNotRegisteredException(String msg) {
        super(msg);
        log.error(msg);
    }
}
