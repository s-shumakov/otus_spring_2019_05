package ru.otus.spring01.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private final Scanner scanner;
    private final MessageSource messageSource;

    public InputOutputServiceImpl(
            Scanner scanner,
            MessageSource messageSource) {
        this.scanner = scanner;
        this.messageSource = messageSource;
    }

    @Override
    public String nextLine() {
        return scanner.nextLine().trim();
    }

    @Override
    public boolean hasNext(String pattern) {
        return scanner.hasNext(pattern);
    }

    @Override
    public String next() {
        return scanner.next();
    }
}
