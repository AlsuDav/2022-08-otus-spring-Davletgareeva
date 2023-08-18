package ru.otus.spring.hw11.repositories.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import ru.otus.spring.hw11.repositories.GenreRepository;
import ru.otus.spring.hw11.entity.Genre;
import ru.otus.spring.hw11.exception.CannotInsertException;
import ru.otus.spring.hw11.exception.CannotUpdateException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Dao для работы с жанрами книг (genre) должно")
@JdbcTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJdbcTest {

    private static final int EXPECTED_GENRES_COUNT = 5;
    private static final long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_NAME = "Action and Adventure";
    private static final String NOT_EXISTING_GENRE_NAME = "Historical";

    @Autowired
    private GenreRepository genreRepository;

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
        long actualGenresCount = genreRepository.count();
        assertThat(actualGenresCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }


    @DisplayName("добавлять жанр в БД, если его не существует")
    @Test
    void shouldInsertGenre_whenGenreNotExist() {
        Genre expectedGenre = Genre.builder().genreName(NOT_EXISTING_GENRE_NAME).build();
        genreRepository.insert(expectedGenre);
        Genre actualGenre = genreRepository.getByName(expectedGenre.getGenreName());
        assertThat(actualGenre.getGenreName()).isEqualTo(expectedGenre.getGenreName());
    }

    @DisplayName("не добавлять жанр в БД, если он уже существует")
    @Test
    void shouldNotInsertGenre_whenGenreExist() {
        Genre expectedGenre = new Genre(6L, EXISTING_GENRE_NAME);

        assertThatThrownBy(() -> genreRepository.insert(expectedGenre))
                .isInstanceOf(CannotInsertException.class);
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreRepository.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("возвращать ожидаемый жанр по его имени (genre_name)")
    @Test
    void shouldReturnExpectedGenreByName() {
        Genre expectedGenre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
        Genre actualGenre = genreRepository.getByName(expectedGenre.getGenreName());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectDeleteGenreById() {
        assertThatCode(() -> genreRepository.getById(EXISTING_GENRE_ID))
                .doesNotThrowAnyException();

        genreRepository.deleteById(EXISTING_GENRE_ID);

        assertThatThrownBy(() -> genreRepository.getById(EXISTING_GENRE_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("возвращать ожидаемый список авторов")
    @Test
    void shouldReturnExpectedGenresList() {
        List<Genre> actualGenreList = genreRepository.getAll();
        assertThat(actualGenreList).hasSize(EXPECTED_GENRES_COUNT);

    }

    @DisplayName("обновлять данные по жанру")
    @Test
    void shouldUpdateGenre() {
        Genre genre = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME + "upd");
        genreRepository.update(genre);
        Genre updatedGenre = genreRepository.getById(genre.getId());
        assertThat(updatedGenre).usingRecursiveComparison().isEqualTo(genre);

    }

    @DisplayName("не обновлять данные по жанру, если его нет в бд")
    @Test
    void shouldNotUpdateGenre_whenGenreNotExist() {
        Genre genre = new Genre(100L, EXISTING_GENRE_NAME + "upd");
        assertThatThrownBy(() -> genreRepository.update(genre))
                .isInstanceOf(CannotUpdateException.class);

    }
}
