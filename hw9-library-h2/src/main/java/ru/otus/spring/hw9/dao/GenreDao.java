package ru.otus.spring.hw9.dao;

import ru.otus.spring.hw9.domain.Genre;

import java.util.List;

public interface GenreDao {

    int count();

    void insert(Genre person);

    Genre getById(long id);

    List<Genre> getAll();

    void deleteById(long id);
}

