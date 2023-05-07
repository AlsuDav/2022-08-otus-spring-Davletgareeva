package ru.otus.spring.hw11.dao;

import ru.otus.spring.hw11.domain.Genre;

import java.util.List;

public interface GenreDao {

    Long count();

    void insert(Genre genre);

    Genre getById(long id);

    Genre getByName(String genreName);

    List<Genre> getAll();

    void deleteById(long id);

    void update(Genre genre);
}

