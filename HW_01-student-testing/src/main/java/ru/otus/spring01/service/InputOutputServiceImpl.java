package ru.otus.spring01.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.config.ConfigProperties;
import ru.otus.spring01.domain.ConsoleContext;

import java.util.Locale;
import java.util.Scanner;

@Service
public class InputOutputServiceImpl implements InputOutputService {
    private final Scanner scanner;
    private final MessageSource messageSource;
    private final ConsoleContext consoleContext;
    private final Locale locale;

    public InputOutputServiceImpl(
            MessageSource messageSource,
            ConsoleContext consoleContext,
            ConfigProperties configProperties) {
        this.scanner = new Scanner(consoleContext.getInputStream());
        this.messageSource = messageSource;
        this.consoleContext = consoleContext;
        this.locale = new Locale(configProperties.getLocale());
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
    public String printMessage(String messageKey, Object[] objects) {
        String message = this.messageSource.getMessage(messageKey, objects, this.locale);
        consoleContext.getPrintStream().println(message);
        return message;
    }

    @Override
    public String printMessage(String messageKey) {
        return printMessage(messageKey, null);
    }

    @Override
    public String println(String message) {
        consoleContext.getPrintStream().println(message);
        return message;
    }
}
