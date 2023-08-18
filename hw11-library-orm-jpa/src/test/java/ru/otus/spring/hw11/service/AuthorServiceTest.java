package ru.otus.spring.hw11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.hw11.repositories.AuthorRepository;
import ru.otus.spring.hw11.entity.Author;
import ru.otus.spring.hw11.exception.CannotUpdateException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Service для работы с авторами книг (author) должно")
@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;
    @Mock
    private AuthorRepository authorDao;

    private static final Long EXPECTED_AUTHORS_COUNT = 1L;
    private static final Long EXISTING_AUTHOR_ID = 1L;
    private static final String EXISTING_AUTHOR_NAME = "J.R.R. Tolkien";
    private static final String NOT_EXISTING_AUTHOR_NAME = "Antoine Marie Jean-Baptiste Roger vicomte de Saint-Exupéry";

    private static final Author EXISTING_AUTHOR = new Author(11L, EXISTING_AUTHOR_NAME);
    private static final Author NOT_EXISTING_AUTHOR = new Author(100L, NOT_EXISTING_AUTHOR_NAME);

    @BeforeEach
    void setUp() {
        Mockito.lenient()
                .when(authorDao.count())
                .thenReturn(EXPECTED_AUTHORS_COUNT);

        Mockito.lenient()
                .doNothing()
                .when(authorDao).insert(EXISTING_AUTHOR);

        Mockito.lenient()
                .doNothing()
                .when(authorDao).insert(NOT_EXISTING_AUTHOR);

        Mockito.lenient()
                .when(authorDao.getById(EXISTING_AUTHOR_ID))
                .thenReturn(EXISTING_AUTHOR);

        Mockito.lenient()
                .when(authorDao.getByName(EXISTING_AUTHOR_NAME))
                .thenReturn(EXISTING_AUTHOR);

        Mockito.lenient()
                .when(authorDao.getAll())
                .thenReturn(List.of(EXISTING_AUTHOR));

        Mockito.lenient()
                .doNothing()
                .when(authorDao).update(EXISTING_AUTHOR);

    }

    @DisplayName("возвращать ожидаемое количество авторов в БД")
    @Test
    void shouldReturnExpectedAuthorCount() {
        Long actualAuthorsCount = authorService.getCountOfAuthors();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @DisplayName("добавлять автора в БД")
    @Test
    void shouldInsertAuthor() {
        assertThatCode(() -> authorService.addAuthor(NOT_EXISTING_AUTHOR_NAME)).doesNotThrowAnyException();
    }

    @DisplayName("возвращать ожидаемого автора по его id")
    @Test
    void shouldReturnExpectedAuthorById() {
        Author actualAuthor = authorService.getAuthorById(EXISTING_AUTHOR_ID);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
    }

    @DisplayName("возвращать ожидаемого автора по его имени (name)")
    @Test
    void shouldReturnExpectedAuthorByName() {
        Author actualAuthor = authorService.getAuthorByName(EXISTING_AUTHOR_NAME);
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(EXISTING_AUTHOR);
    }

    @DisplayName("удалять заданного автора по его id")
    @Test
    void shouldCorrectDeleteAuthorById() {
        assertThatCode(() -> authorService.getAuthorById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();
        assertThatCode(() -> authorService.deleteAuthor(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedAuthorsList() {
        List<Author> actualAuthorList = authorService.getAllAuthors();
        assertThat(actualAuthorList).hasSize(EXPECTED_AUTHORS_COUNT.intValue());

    }

    @DisplayName("обновлять данные об авторе")
    @Test
    void shouldUpdateAuthor() {
        assertThatCode(() -> authorService.updateAuthor(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME)).doesNotThrowAnyException();
    }

    @DisplayName("не обновлять данные об авторе, который пришел без id")
    @Test
    void shouldNotUpdateAuthor_whenIdNull() {
        assertThatThrownBy(() -> authorService.updateAuthor(null, NOT_EXISTING_AUTHOR_NAME))
                .isInstanceOf(CannotUpdateException.class);
    }

}
