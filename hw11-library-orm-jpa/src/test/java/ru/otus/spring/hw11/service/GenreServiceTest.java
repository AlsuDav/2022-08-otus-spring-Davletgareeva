package ru.otus.spring.hw11.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.hw11.dao.GenreDao;
import ru.otus.spring.hw11.domain.Genre;
import ru.otus.spring.hw11.exception.CannotUpdateException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Service для работы с жанрами книг должно")
@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @InjectMocks
    private GenreService genreService;
    @Mock
    private GenreDao genreDao;

    private static final Long EXPECTED_GENRES_COUNT = 1L;
    private static final long EXISTING_GENRE_ID = 1L;
    private static final String EXISTING_GENRE_NAME = "Action and Adventure";
    private static final String NOT_EXISTING_GENRE_NAME = "Historical";
    private static final Genre EXISTING_GENRE = new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
    private static final Genre NOT_EXISTING_GENRE = new Genre(100L, NOT_EXISTING_GENRE_NAME);

    @BeforeEach
    void setUp() {
        Mockito.lenient()
                .when(genreDao.count())
                .thenReturn(EXPECTED_GENRES_COUNT);

        Mockito.lenient()
                .doNothing()
                .when(genreDao).insert(EXISTING_GENRE);

        Mockito.lenient()
                .doNothing()
                .when(genreDao).deleteById(EXISTING_GENRE_ID);

        Mockito.lenient()
                .doNothing()
                .when(genreDao).insert(NOT_EXISTING_GENRE);

        Mockito.lenient()
                .when(genreDao.getById(EXISTING_GENRE_ID))
                .thenReturn(EXISTING_GENRE);

        Mockito.lenient()
                .when(genreDao.getByName(EXISTING_GENRE_NAME))
                .thenReturn(EXISTING_GENRE);

        Mockito.lenient()
                .when(genreDao.getAll())
                .thenReturn(List.of(EXISTING_GENRE));

        Mockito.lenient()
                .doNothing()
                .when(genreDao).update(EXISTING_GENRE);
    }

    @DisplayName("возвращать ожидаемое количество жанров в БД")
    @Test
    void shouldReturnExpectedGenreCount() {
        Long actualAuthorsCount = genreService.getCountOfGenres();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_GENRES_COUNT);
    }

    @DisplayName("добавлять жанр в БД")
    @Test
    void shouldInsertGenre() {
        assertThatCode(() -> genreService.addGenre(NOT_EXISTING_GENRE_NAME)).doesNotThrowAnyException();
    }

    @DisplayName("возвращать ожидаемый жанр по его id")
    @Test
    void shouldReturnExpectedGenreById() {
        Genre actualGenre = genreService.getGenreById(EXISTING_GENRE_ID);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
    }

    @DisplayName("возвращать ожидаемый жанр по его имени (genreName)")
    @Test
    void shouldReturnExpectedGenreByName() {
        Genre actualGenre = genreService.getGenreByName(EXISTING_GENRE_NAME);
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(EXISTING_GENRE);
    }

    @DisplayName("удалять заданный жанр по его id")
    @Test
    void shouldCorrectDeleteGenreById() {

        assertThatCode(() -> genreService.deleteGenre(EXISTING_GENRE_ID)).doesNotThrowAnyException();

    }

    @DisplayName("возвращать ожидаемый список жанров")
    @Test
    void shouldReturnExpectedGenreList() {
        List<Genre> actualGenreList = genreService.getAllGenres();
        assertThat(actualGenreList).hasSize(EXPECTED_GENRES_COUNT.intValue());

    }

    @DisplayName("обновлять данные о жанре")
    @Test
    void shouldUpdateGenre() {
        assertThatCode(() -> genreService.updateGenre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME)).doesNotThrowAnyException();
    }

    @DisplayName("не обновлять данные о жанре, который пришел без id")
    @Test
    void shouldNotUpdateGenre_whenIdNull() {
        assertThatThrownBy(() -> genreService.updateGenre(null, NOT_EXISTING_GENRE_NAME))
                .isInstanceOf(CannotUpdateException.class);
    }

}

