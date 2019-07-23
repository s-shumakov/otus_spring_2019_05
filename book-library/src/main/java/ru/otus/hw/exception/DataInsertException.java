package ru.otus.hw.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DataInsertException extends DataIntegrityViolationException {
    public DataInsertException(String msg) {
        super(msg);
    }

    public DataInsertException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
