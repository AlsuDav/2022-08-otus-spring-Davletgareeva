package ru.otus.spring.hw11.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.hw11.service.AuthorService;
import ru.otus.spring.hw11.service.BookService;
import ru.otus.spring.hw11.service.GenreService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {


    private String userName;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookService bookService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "AnyUser") String userName) {
        this.userName = userName;
        return String.format("Добро пожаловать в онлайн-библиотеку, %s", userName);
    }

    @ShellMethod(value = "Show all authors command", key = {"authors"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getAuthors() {
        System.out.println(authorService.getAllAuthors());
        return "Список всех авторов.";
    }

    @ShellMethod(value = "Show author by id command", key = {"author", "a"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getAuthorById(Long id) {
        System.out.println(authorService.getAuthorById(id));
        return String.format("Автор с id=%d.", id);
    }

    @ShellMethod(value = "Show author by name command", key = {"author by name"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getAuthorByName(String name) {
        System.out.println(authorService.getAuthorByName(name));
        return String.format("Автор с name=%s.", name);
    }

    @ShellMethod(value = "Show count of all authors command", key = {"authors count"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getAuthorsCount() {
        System.out.println(authorService.getCountOfAuthors());
        return "Количество всех авторов.";
    }

    @ShellMethod(value = "Add new author command", key = {"author add"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String addAuthor(String authorName) {
        authorService.addAuthor(authorName);
        return "Автор добавлен в базу данных с id=%d.".formatted(authorService.getAuthorByName(authorName).getId());
    }

    @ShellMethod(value = "Delete author command", key = {"author delete"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteAuthor(Long authorId) {
        authorService.deleteAuthor(authorId);
        return "Автор с id=%d удален из базы данных.".formatted(authorId);
    }

    @ShellMethod(value = "Update author command", key = {"author update"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String updateAuthor(Long authorId, String updatedName) {
        authorService.updateAuthor(authorId, updatedName);
        return "Автор с id=%d обновлен в базе данных.".formatted(authorId);
    }

    @ShellMethod(value = "Show all genres command", key = {"genres"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getGenres() {
        System.out.println(genreService.getAllGenres());
        return "Список всех доступных жанров.";
    }

    @ShellMethod(value = "Show genre by id command", key = {"genre", "g"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getGenreById(Long id) {
        System.out.println(genreService.getGenreById(id));
        return String.format("Жанр с id=%d.", id);
    }

    @ShellMethod(value = "Show genre by name command", key = {"genre by name"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getGenreByName(String name) {
        System.out.println(genreService.getGenreByName(name));
        return String.format("Жанр с genreName=%s.", name);
    }

    @ShellMethod(value = "Show count of all genres command", key = {"genres count"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getGenresCount() {
        System.out.println(genreService.getCountOfGenres());
        return "Количество всех жанров.";
    }

    @ShellMethod(value = "Add new genre command", key = {"genre add"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String addGenre(String genreName) {
        genreService.addGenre(genreName);
        return "Жанр добавлен в базу данных с id=%d.".formatted(genreService.getGenreByName(genreName).getId());
    }

    @ShellMethod(value = "Delete genre command", key = {"genre delete"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteGenre(Long genreId) {
        genreService.deleteGenre(genreId);
        return "Жанр с id=%d удален из базы данных.".formatted(genreId);
    }

    @ShellMethod(value = "Update genre command", key = {"genre update"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String updateGenre(Long genreId, String updatedName) {
        genreService.updateGenre(genreId, updatedName);
        return "Жанр с id=%d обновлен в базе данных.".formatted(genreId);
    }

    @ShellMethod(value = "Show all books command", key = {"books"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getBooks() {
        System.out.println(bookService.getAllBooks());
        return "Список всех доступных книг.";
    }

    @ShellMethod(value = "Show Book by id command", key = {"book", "b"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getBookById(Long id) {
        System.out.println(bookService.getBookById(id));
        return String.format("Книга с id=%d.", id);
    }

    @ShellMethod(value = "Show Book by name command", key = {"book by name"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getBookByName(String name) {
        System.out.println(bookService.getBookByName(name));
        return String.format("Книга с названием: %s.", name);
    }

    @ShellMethod(value = "Show count of all books command", key = {"books count"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String getBooksCount() {
        System.out.println(bookService.getCountOfBooks());
        return "Количество всех книг.";
    }

    @ShellMethod(value = "Add new book command", key = {"book add"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String addBook(String bookName, List<String> authorNames, List<String> genreNames) {
        bookService.addBook(bookName, authorNames, genreNames);
        return "Книга добавлен в базу данных с id=%d.".formatted(bookService.getBookByName(bookName).getId());
    }

    @ShellMethod(value = "Delete book command", key = {"book delete"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String deleteBook(Long bookId) {
        bookService.deleteBook(bookId);
        return "Книга с id=%d удалена из базы данных.".formatted(bookId);
    }

    //book update --bookId 1 --updatedName 'THE HOBBIT upd' --authorNames 'Tolkien' 'Anonim' --genreNames 'Adventure'
    @ShellMethod(value = "Update book command", key = {"book update"})
    @ShellMethodAvailability(value = "isPublishEventCommandAvailable")
    public String updateBook(Long bookId, String updatedName, List<String> authorNames, List<String> genreNames) {
        bookService.updateBook(bookId, updatedName, authorNames, genreNames);
        return "Книга с id=%d обновлена в базе данных.".formatted(bookId);
    }

    private Availability isPublishEventCommandAvailable() {
        return userName == null ? Availability.unavailable("Перед работой с электронной библиотекой просим пройти регистрацию. Для этого введите команду l или login") : Availability.available();
    }
}