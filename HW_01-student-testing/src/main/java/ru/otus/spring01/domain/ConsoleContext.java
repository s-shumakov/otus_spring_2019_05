package ru.otus.spring01.domain;

import java.io.InputStream;
import java.io.PrintStream;

public class ConsoleContext {
    private PrintStream printStream;
    private InputStream inputStream;

    public ConsoleContext(PrintStream printStream, InputStream inputStream) {
        this.printStream = printStream;
        this.inputStream = inputStream;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }

    public void setPrintStream(PrintStream printStream) {
        this.printStream = printStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
}
