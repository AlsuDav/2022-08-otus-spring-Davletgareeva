package ru.otus.spring.hw9.dao;

import ru.otus.spring.hw9.domain.Author;

import java.util.List;

public interface AuthorDao {

    int count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

    Author getByName(String name);

}

