package ru.otus.spring.hw11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.hw11.dao.BookDao;
import ru.otus.spring.hw11.domain.Author;
import ru.otus.spring.hw11.domain.Book;
import ru.otus.spring.hw11.domain.Genre;
import ru.otus.spring.hw11.exception.CannotInsertException;
import ru.otus.spring.hw11.exception.CannotUpdateException;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Service для работы с книгами должно")
@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookDao bookDao;

    private static final Long EXPECTED_BOOKS_COUNT = 1L;
    private static final Long NOT_EXISTING_BOOK_ID = 1000L;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_NAME = "The Hobbit";
    private static final String NOT_EXISTING_BOOK_NAME = "New Book";
    private static final String NOT_EXISTING_AUTHOR_NAME_1 = "New Author1";
    private static final String EXISTING_AUTHOR_NAME_1 = "J.R.R. Tolkien";
    private static final Long EXISTING_AUTHOR_ID_1 = 1L;
    private static final String EXISTING_AUTHOR_NAME_2 = "Anonymous";
    private static final Long EXISTING_AUTHOR_ID_2 = 11L;
    private static final String NOT_EXISTING_GENRE_NAME_1 = "New genre 1";
    private static final String NOT_EXISTING_GENRE_NAME_2 = "New genre 2";
    private static final String EXISTING_GENRE_NAME_1 = "Action and Adventure";
    private static final Long EXISTING_GENRE_ID_1 = 1L;
    private static final String EXISTING_GENRE_NAME_2 = "Drama";
    private static final Long EXISTING_GENRE_ID_2 = 5L;
    private static final Author EXISTING_AUTHOR_1 = Author.builder().id(EXISTING_AUTHOR_ID_1).name(EXISTING_AUTHOR_NAME_1).build();
    private static final Author EXISTING_AUTHOR_2 = Author.builder().id(EXISTING_AUTHOR_ID_2).name(EXISTING_AUTHOR_NAME_2).build();
    private static final Author NOT_EXISTING_AUTHOR = Author.builder().id(100L).name(NOT_EXISTING_AUTHOR_NAME_1).build();
    private static final Genre EXISTING_GENRE_1 = Genre.builder().id(EXISTING_GENRE_ID_1).genreName(EXISTING_GENRE_NAME_1).build();
    private static final Genre EXISTING_GENRE_2 = Genre.builder().id(EXISTING_GENRE_ID_2).genreName(EXISTING_GENRE_NAME_2).build();
    private static final Genre NOT_EXISTING_GENRE = Genre.builder().id(100L).genreName(NOT_EXISTING_GENRE_NAME_1).build();

    private static final Book NOT_EXISTING_BOOK_1 = Book.builder()
            .id(NOT_EXISTING_BOOK_ID)
            .bookName(NOT_EXISTING_BOOK_NAME)
            .authors(Set.of(EXISTING_AUTHOR_1, EXISTING_AUTHOR_2))
            .genres(Set.of(EXISTING_GENRE_1, EXISTING_GENRE_2))
            .build();

    private static final Book NOT_EXISTING_BOOK_2 = Book.builder()
            .bookName(NOT_EXISTING_BOOK_NAME)
            .authors(Set.of(NOT_EXISTING_AUTHOR))
            .genres(Set.of(NOT_EXISTING_GENRE))
            .build();

    private static final Book EXISTING_BOOK = Book.builder()
            .id(EXISTING_BOOK_ID)
            .bookName(EXISTING_BOOK_NAME)
            .authors(Set.of(EXISTING_AUTHOR_1, EXISTING_AUTHOR_2))
            .genres(Set.of(EXISTING_GENRE_1, EXISTING_GENRE_2))
            .build();

    @BeforeEach
    void setUp() {
        Mockito.lenient()
                .when(bookDao.count())
                .thenReturn(EXPECTED_BOOKS_COUNT);

        Mockito.lenient()
                .doThrow(new CannotInsertException(""))
                .when(bookDao).insert(EXISTING_BOOK);

        Mockito.lenient()
                .doNothing()
                .when(bookDao).deleteById(NOT_EXISTING_BOOK_ID);

        Mockito.lenient()
                .doNothing()
                .when(bookDao).insert(NOT_EXISTING_BOOK_1);

        Mockito.lenient()
                .doNothing()
                .when(bookDao).deleteById(EXISTING_BOOK.getId());

        Mockito.lenient()
                .when(bookDao.getById(EXISTING_BOOK_ID))
                .thenReturn(EXISTING_BOOK);

        Mockito.lenient()
                .when(bookDao.getById(NOT_EXISTING_BOOK_ID))
                .thenReturn(null);

        Mockito.lenient()
                .when(bookDao.getByName(EXISTING_BOOK.getBookName()))
                .thenReturn(EXISTING_BOOK);

        Mockito.lenient()
                .when(bookDao.getByName(NOT_EXISTING_BOOK_1.getBookName()))
                .thenReturn(null);

        Mockito.lenient()
                .when(bookDao.getByName(NOT_EXISTING_BOOK_2.getBookName()))
                .thenReturn(null);

        Mockito.lenient()
                .when(bookDao.getAll())
                .thenReturn(List.of(EXISTING_BOOK));

        Mockito.lenient()
                .doNothing()
                .when(bookDao).update(EXISTING_BOOK);
    }

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualAuthorsCount = bookService.getCountOfBooks();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД, когда жанр и авторы существуют, а книга нет")
    @Test
    void shouldInsertAuthor_whenBookNotExistButGenreAndAuthorExist() {
        assertThatCode(() -> bookService.addBook(NOT_EXISTING_BOOK_1.getBookName(), NOT_EXISTING_BOOK_1.getAuthors().stream().map(Author::getName).toList(),
                NOT_EXISTING_BOOK_1.getGenres().stream().map(Genre::getGenreName).toList()))
                .doesNotThrowAnyException();
            }

    @DisplayName("не добавлять книгу в БД, когда жанр и авторы существуют, а книга нет")
    @Test
    void shouldNotInsertAuthor_whenBookAlreadyExist() {
        assertThatThrownBy(() -> bookService.addBook(EXISTING_BOOK.getBookName(), List.of(""), List.of("")))
                .isInstanceOf(CannotInsertException.class);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookService.getBookById(EXISTING_BOOK.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_BOOK);
    }

    @DisplayName("возвращать null, если id не найдено")
    @Test
    void shouldNotReturnExpectedBookByNotExistingId() {

        Book actualBook = bookService.getBookById(NOT_EXISTING_BOOK_ID);
        assertThat(actualBook).isNull();
    }

    @DisplayName("возвращать ожидаемую книгу по ее имени")
    @Test
    void shouldReturnExpectedAuthorByName() {

        Book actualBook = bookService.getBookByName(EXISTING_BOOK_NAME);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_BOOK);

    }

    @DisplayName("возвращать null, если имя (bookName) не найдено")
    @Test
    void shouldNotReturnExpectedBookByNotExistingName() {

        Book actualBook = bookService.getBookByName(NOT_EXISTING_BOOK_NAME);
        assertThat(actualBook).isNull();
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookService.getBookById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();
        assertThatCode(() -> bookService.deleteBook(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Book> actualBookList = bookService.getAllBooks();
        assertThat(actualBookList).hasSize(EXPECTED_BOOKS_COUNT.intValue());

    }

    @DisplayName("обновлять данные о книге")
    @Test
    void shouldUpdateBook() {
        assertThatCode(() -> bookService.updateBook(EXISTING_BOOK.getId(), EXISTING_BOOK.getBookName(), EXISTING_BOOK.getAuthors().stream().map(Author::getName).toList(),
                EXISTING_BOOK.getGenres().stream().map(Genre::getGenreName).toList()))
                .doesNotThrowAnyException();
    }

    @DisplayName("не обновлять данные о книге, которая пришла без id")
    @Test
    void shouldNotUpdateBook_whenIdNull() {
        assertThatThrownBy(() -> bookService.updateBook(null, EXISTING_BOOK.getBookName(), EXISTING_BOOK.getAuthors().stream().map(Author::getName).toList(),
                EXISTING_BOOK.getGenres().stream().map(Genre::getGenreName).toList()))
                .isInstanceOf(CannotUpdateException.class);
    }
}

