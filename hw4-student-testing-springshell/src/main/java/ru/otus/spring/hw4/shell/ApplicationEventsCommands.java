package ru.otus.spring.hw4.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw4.service.ExamService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {


    private String userName;
    private final ExamService examService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать: %s", userName);
    }

    @ShellMethod(value = "Publish event command", key = {"run", "start", "go"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String exam() {
        examService.startExam();
        return "Тестирование завершено.";
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null? Availability.unavailable("Перед запуском тестирования просим пройти регистрацию. Для этого введите команду l или login"): Availability.available();
    }
}