package ru.otus.spring01.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring01.service.InputOutputServiceImpl;
import ru.otus.spring01.service.TestingService;

@ShellComponent
public class TestingServiceCommands {
    private final InputOutputServiceImpl inputOutputService;
    private final TestingService testingService;
    private String userName;

    public TestingServiceCommands(InputOutputServiceImpl inputOutputService, TestingService testingService) {
        this.inputOutputService = inputOutputService;
        this.testingService = testingService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Anonymous") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Publish event command", key = {"r", "run", "runtest"})
    @ShellMethodAvailability(value = "isStartTestCommandAvailable")
    public void runTest() {
        testingService.runTest();
    }

    private Availability isStartTestCommandAvailable() {
        return userName == null? Availability.unavailable("login first (l, login: Login command)"): Availability.available();
    }
}
