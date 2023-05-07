package ru.otus.spring.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw9.dao.GenreDao;
import ru.otus.spring.hw9.domain.Genre;
import ru.otus.spring.hw9.exception.CannotUpdateException;
import ru.otus.spring.hw9.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao genreDao;

    public List<Genre> getAllGenres() {
        return genreDao.getAll();
    }

    public Genre getGenreByName(String genreName) {
        return genreDao.getByName(genreName);
    }

    public Genre getGenreById(Long genreId) {
        try {
            return genreDao.getById(genreId);
        } catch (
                EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр с id=%d не найден".formatted(genreId));
        }
    }

    public void addGenre(String genreName) {
        Genre genre = Genre.builder().genreName(genreName).build();
        genreDao.insert(genre);
    }

    public void deleteGenre(Long id) {
        genreDao.deleteById(id);

    }

    public Long getCountOfGenres() {
        return genreDao.count();
    }

    public void updateGenre(Long id, String genreName) {
        if (id == null) {
            throw new CannotUpdateException("Book id should not be null");
        }
        genreDao.update(Genre.builder().id(id).genreName(genreName).build());
    }

}
