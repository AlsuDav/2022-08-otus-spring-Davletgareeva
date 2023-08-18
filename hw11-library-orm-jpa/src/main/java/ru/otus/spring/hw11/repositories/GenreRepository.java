package ru.otus.spring.hw11.repositories;

import ru.otus.spring.hw11.entity.Genre;

import java.util.List;

public interface GenreRepository {

    Long count();

    void insert(Genre genre);

    Genre getById(long id);

    Genre getByName(String genreName);

    List<Genre> getAll();

    void deleteById(long id);

    void update(Genre genre);
}

