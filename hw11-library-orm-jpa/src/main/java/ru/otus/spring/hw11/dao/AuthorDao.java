package ru.otus.spring.hw11.dao;

import ru.otus.spring.hw11.domain.Author;

import java.util.List;

public interface AuthorDao {

    Long count();

    void insert(Author author);

    Author getById(long id);

    List<Author> getAll();

    void deleteById(long id);

    Author getByName(String name);

    void update(Author author);

}

