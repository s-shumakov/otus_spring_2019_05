package ru.otus.spring01.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring01.service.InputOutputService;
import ru.otus.spring01.service.TestingService;

@ShellComponent
public class TestingServiceCommands {
    private final InputOutputService inputOutputService;
    private final TestingService testingService;
    private static final String WELCOME = "welcome.message";
    private String login;
    private String userName;

    public TestingServiceCommands(InputOutputService inputOutputService, TestingService testingService) {
        this.inputOutputService = inputOutputService;
        this.testingService = testingService;
    }

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "Anonymous") String login) {
        this.setLogin(login);
        inputOutputService.printMessage(WELCOME, new String[]{login});
    }

    @ShellMethod(value = "Run test command", key = {"r", "run", "runtest"})
    @ShellMethodAvailability(value = "isStartTestCommandAvailable")
    public void runTest() {
        this.setUserName(testingService.getUserName());
        testingService.runTest(this.getUserName());
    }

    @ShellMethod(value = "Get result command", key = {"res", "result"})
    @ShellMethodAvailability(value = "isStartTestCommandAvailable")
    public void getResult() {
        testingService.getResult(this.getUserName());
    }

    private Availability isStartTestCommandAvailable() {
        return this.getLogin() == null? Availability.unavailable("login first (l, login: Login command)"): Availability.available();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
