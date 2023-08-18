package ru.otus.spring.hw11.repositories;

import ru.otus.spring.hw11.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Long count();

    Author insert(Author author);

    Optional<Author> getById(long id);

    List<Author> getAll();

    void deleteById(long id);

    Author getByName(String name);

    void update(Author author);

}

