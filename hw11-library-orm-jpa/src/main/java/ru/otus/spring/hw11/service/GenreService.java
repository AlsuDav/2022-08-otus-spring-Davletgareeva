package ru.otus.spring.hw11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw11.repositories.GenreRepository;
import ru.otus.spring.hw11.entity.Genre;
import ru.otus.spring.hw11.exception.CannotUpdateException;
import ru.otus.spring.hw11.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    public Genre getGenreByName(String genreName) {
        return genreRepository.getByName(genreName);
    }

    public Genre getGenreById(Long genreId) {
        try {
            return genreRepository.getById(genreId);
        } catch (
                EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр с id=%d не найден".formatted(genreId));
        }
    }

    public void addGenre(String genreName) {
        Genre genre = Genre.builder().genreName(genreName).build();
        genreRepository.insert(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);

    }

    public Long getCountOfGenres() {
        return genreRepository.count();
    }

    public void updateGenre(Long id, String genreName) {
        if (id == null) {
            throw new CannotUpdateException("Book id should not be null");
        }
        genreRepository.update(Genre.builder().id(id).genreName(genreName).build());
    }

}
