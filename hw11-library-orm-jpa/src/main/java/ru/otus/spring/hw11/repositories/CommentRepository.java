package ru.otus.spring.hw11.repositories;

import ru.otus.spring.hw11.entity.Author;
import ru.otus.spring.hw11.entity.Comment;

import java.util.List;

public interface CommentRepository {
    Long count();

    void insert(Comment comment);

    Comment getById(long id);

    List<Comment> getAll();

    void deleteById(long id);

    List<Comment> getByBookId(Long bookId);

    void update(Comment comment);
}
