package ru.otus.spring01.service;

import java.util.Locale;

public interface InputOutputService {
    String nextLine();
    boolean hasNext(String pattern);
    String next();
    String println(String messageKey, Object[] objects, Locale locale);
    String println(String messageKey, Locale locale);
    String println(String message);
}
