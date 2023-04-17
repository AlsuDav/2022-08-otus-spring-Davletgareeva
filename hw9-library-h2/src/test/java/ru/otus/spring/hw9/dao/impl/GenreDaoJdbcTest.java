package ru.otus.spring.hw9.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.hw9.domain.Genre;
import ru.otus.spring.hw9.exception.CannotInsertException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с жанрами книг (genre) должно")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 5;
    private static final long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_NAME = "Action and Adventure";
    private static final String NOT_EXISTING_GENRE_NAME = "Historical";

    @Autowired
    private GenreDaoJdbc genreDaoJdbc;

    @BeforeTransaction
    void beforeTransaction(){
        System.out.println("beforeTransaction");
    }

    @AfterTransaction
    void afterTransaction(){
        System.out.println("afterTransaction");
    }

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        int actualGenresCount = genreDaoJdbc.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }


    @DisplayName("добавлять жанр в БД, если его не существует")
    @Test
    void shouldInsertGenre_whenGenreNotExist() {
        Genre expectedGenre = new Genre(6L, NOT_EXISTING_GENRE_NAME);
        genreDaoJdbc.insert(expectedGenre);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(actualGenre);
    }

    @DisplayName("не добавлять жанр в БД, если он уже существует")
    @Test
    void shouldNotInsertGenre_whenGenreExist() {
        Genre expectedGenre = new Genre(6L, EXISTING_GENRE_NAME);

        assertThatThrownBy(() -> genreDaoJdbc.insert(expectedGenre))
                .isInstanceOf(CannotInsertException.class);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDaoJdbc.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его имени (genre_name)")
    @Test
    void shouldReturnExpectedGenreByName() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreDaoJdbc.getByName(expectedGenre.getGenreName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> genreDaoJdbc.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreDaoJdbc.deleteById(EXISTING_GENRE_ID);

        assertThatThrownBy(() -> genreDaoJdbc.getById(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedGenresList() {
        List<Genre> actualGenreList = genreDaoJdbc.getAll();
        assertThat(actualGenreList).hasSize(EXPECTED_GENRES_COUNT);

    }
}
