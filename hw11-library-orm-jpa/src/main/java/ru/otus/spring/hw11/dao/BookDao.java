package ru.otus.spring.hw11.dao;

import ru.otus.spring.hw11.domain.Book;

import java.util.List;

public interface BookDao {

    Long count();

    void insert(Book book);

    Book getById(long id);

    Book getByName(String name);

    List<Book> getAll();

    void deleteById(long id);

    void update(Book book);
}

