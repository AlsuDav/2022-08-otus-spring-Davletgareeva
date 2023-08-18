package ru.otus.spring.hw11.repositories;

import ru.otus.spring.hw11.entity.Book;

import java.util.List;

public interface BookRepository {

    Long count();

    void insert(Book book);

    Book getById(long id);

    Book getByName(String name);

    List<Book> getAll();

    void deleteById(long id);

    void update(Book book);
}

