package ru.otus.spring01.service;

import java.util.Locale;

public interface InputOutputService {
    String nextLine();
    boolean hasNext(String pattern);
    String next();
    String printMessage(String messageKey, Object[] objects);
    String printMessage(String messageKey);
    String println(String message);
}
