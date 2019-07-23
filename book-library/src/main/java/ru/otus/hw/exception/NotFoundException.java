package ru.otus.hw.exception;

import org.springframework.dao.DataAccessException;

public class NotFoundException extends DataAccessException {
    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
