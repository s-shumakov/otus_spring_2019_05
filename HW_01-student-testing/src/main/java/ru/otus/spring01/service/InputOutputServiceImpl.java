package ru.otus.spring01.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.domain.ConsoleContext;

import java.util.Locale;
import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private final Scanner scanner;
    private final MessageSource messageSource;
    private final ConsoleContext consoleContext;

    public InputOutputServiceImpl(MessageSource messageSource, ConsoleContext consoleContext) {
        this.scanner = new Scanner(consoleContext.getInputStream());
        this.messageSource = messageSource;
        this.consoleContext = consoleContext;
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
    public String println(String messageKey, Object[] objects, Locale locale) {
        String message = this.messageSource.getMessage(messageKey, objects, locale);
        consoleContext.getPrintStream().println(message);
        return message;
    }

    @Override
    public String println(String messageKey, Locale locale) {
        return println(messageKey, null, locale);
    }

    @Override
    public String println(String message) {
        consoleContext.getPrintStream().println(message);
        return message;
    }
}
