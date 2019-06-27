package ru.otus.spring01.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private final Scanner scanner;
    private final MessageSource messageSource;

    public InputOutputServiceImpl(MessageSource messageSource) {
        this.scanner = new Scanner(System.in);
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

    @Override
    public String println(String message) {
        System.out.println(message);
        return message;
    }
}
