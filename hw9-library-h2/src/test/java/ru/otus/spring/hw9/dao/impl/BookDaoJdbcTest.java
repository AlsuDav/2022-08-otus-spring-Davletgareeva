package ru.otus.spring.hw9.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.hw9.dao.BookDao;
import ru.otus.spring.hw9.domain.Author;
import ru.otus.spring.hw9.domain.Book;
import ru.otus.spring.hw9.domain.Genre;
import ru.otus.spring.hw9.exception.CannotInsertException;
import ru.otus.spring.hw9.exception.CannotUpdateException;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

    @DisplayName("Dao для работы с книгами (book) должно")
    @JdbcTest
    @Import({BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
    class BookDaoJdbcTest {

    private static final int EXPECTED_BOOKS_COUNT = 12;
    private static final Long NOT_EXISTING_BOOK_ID = 1000L;
    private static final Long EXISTING_BOOK_ID = 1L;
    private static final String EXISTING_BOOK_NAME = "The Hobbit";
    private static final String NOT_EXISTING_BOOK_NAME = "New Book";
    private static final String NOT_EXISTING_AUTHOR_NAME_1 = "New Author1";
    private static final String NOT_EXISTING_AUTHOR_NAME_2 = "New Author2";
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


    @Autowired
    private BookDao bookDao;

    @BeforeTransaction
    void beforeTransaction() {
        System.out.println("beforeTransaction");
    }

    @AfterTransaction
    void afterTransaction() {
        System.out.println("afterTransaction");
    }

    @DisplayName("возвращать ожидаемое количество книг в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualAuthorsCount = bookDao.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("добавлять книгу в БД, когда жанр и авторы существуют, а книга нет")
    @Test
    void shouldInsertAuthor_whenBookNotExistButGenreAndAuthorExist() {
        bookDao.insert(NOT_EXISTING_BOOK_1);
        Book actualBook = bookDao.getByName(NOT_EXISTING_BOOK_NAME);
        assertThat(actualBook.getBookName()).isEqualTo(NOT_EXISTING_BOOK_1.getBookName());
    }

    @DisplayName("добавлять книгу в БД, когда жанр и авторы существуют, а книга нет")
    @Test
    void shouldInsertAuthor_whenBookNotExistButGenreAndAuthorNotExist() {

        bookDao.insert(NOT_EXISTING_BOOK_2);
        Book actualBook = bookDao.getByName(NOT_EXISTING_BOOK_2.getBookName());

        assertThat(actualBook.getAuthors()).hasSize(NOT_EXISTING_BOOK_2.getAuthors().size());
        assertThat(actualBook.getGenres()).hasSize(NOT_EXISTING_BOOK_2.getGenres().size());
        assertThat(actualBook.getBookName()).isEqualTo(NOT_EXISTING_BOOK_2.getBookName());
    }

    @DisplayName("добавлять книгу в БД, когда жанр и авторы существуют, а книга нет")
    @Test
    void shouldNotInsertAuthor_whenBookAlreadyExist() {
        Author author1 = Author.builder().name(NOT_EXISTING_AUTHOR_NAME_1).build();
        Author author2 = Author.builder().name(NOT_EXISTING_AUTHOR_NAME_2).build();
        Set<Author> authors = Set.of(author1, author2);

        Genre genre1 = Genre.builder().genreName(NOT_EXISTING_GENRE_NAME_1).build();
        Genre genre2 = Genre.builder().genreName(NOT_EXISTING_GENRE_NAME_2).build();
        Set<Genre> genres = Set.of(genre1, genre2);

        Book book = Book.builder()
                .bookName(EXISTING_BOOK_NAME)
                .authors(authors)
                .genres(genres)
                .build();

        assertThatThrownBy(() -> bookDao.insert(book))
                .isInstanceOf(CannotInsertException.class);
    }

    @DisplayName("возвращать ожидаемую книгу по ее id")
    @Test
    void shouldReturnExpectedBookById() {
        Book actualBook = bookDao.getById(EXISTING_BOOK.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(EXISTING_BOOK);
    }

    @DisplayName("возвращать null, если id не найдено")
    @Test
    void shouldNotReturnExpectedBookByNotExistingId() {

        Book expectedBook = Book.builder()
                .id(NOT_EXISTING_BOOK_ID)
                .build();
        Book actualBook = bookDao.getById(expectedBook.getId());
        assertThat(actualBook).isNull();
    }

    @DisplayName("возвращать ожидаемую книгу по ее имени")
    @Test
    void shouldReturnExpectedAuthorByName() {
        Author author1 = Author.builder().id(EXISTING_AUTHOR_ID_1).name(EXISTING_AUTHOR_NAME_1).build();
        Author author2 = Author.builder().id(EXISTING_AUTHOR_ID_2).name(EXISTING_AUTHOR_NAME_2).build();
        Set<Author> authors = Set.of(author1, author2);

        Genre genre1 = Genre.builder().id(EXISTING_GENRE_ID_1).genreName(EXISTING_GENRE_NAME_1).build();
        Genre genre2 = Genre.builder().id(EXISTING_GENRE_ID_2).genreName(EXISTING_GENRE_NAME_2).build();
        Set<Genre> genres = Set.of(genre1, genre2);

        Book expectedBook = Book.builder()
                .id(EXISTING_BOOK_ID)
                .bookName(EXISTING_BOOK_NAME)
                .authors(authors)
                .genres(genres)
                .build();
        Book actualBook = bookDao.getByName(expectedBook.getBookName());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("возвращать null, если имя (bookName) не найдено")
    @Test
    void shouldNotReturnExpectedBookByNotExistingName() {

        Book expectedBook = Book.builder()
                .id(EXISTING_BOOK_ID)
                .bookName(NOT_EXISTING_BOOK_NAME)
                .build();
        Book actualBook = bookDao.getByName(expectedBook.getBookName());
        assertThat(actualBook).isNull();
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteBookById() {
        assertThatCode(() -> bookDao.getById(EXISTING_BOOK_ID))
                .doesNotThrowAnyException();

        bookDao.deleteById(EXISTING_BOOK_ID);

        assertThat(bookDao.getById(EXISTING_BOOK_ID)).isNull();
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Book> actualBookList = bookDao.getAll();
        assertThat(actualBookList).hasSize(EXPECTED_BOOKS_COUNT);
    }

    @DisplayName("обновлять данные по книге")
    @Test
    void shouldUpdateGenre() {
        Book book = Book.builder()
                .id(EXISTING_BOOK_ID)
                .bookName(EXISTING_BOOK_NAME + "upd")
                .authors(Set.of(EXISTING_AUTHOR_1, EXISTING_AUTHOR_2))
                .genres(Set.of(EXISTING_GENRE_1, EXISTING_GENRE_2))
                .build();

        bookDao.update(book);
        Book updatedBook = bookDao.getById(book.getId());
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(book);

    }

    @DisplayName("не обновлять данные по книге, если ее нет в бд")
    @Test
    void shouldNotUpdateBook_whenItNotExist() {
        assertThatThrownBy(() -> bookDao.update(NOT_EXISTING_BOOK_1))
                .isInstanceOf(CannotUpdateException.class);

    }
}
