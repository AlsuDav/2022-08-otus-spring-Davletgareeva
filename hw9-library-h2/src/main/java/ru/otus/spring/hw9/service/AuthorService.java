package ru.otus.spring.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw9.dao.AuthorDao;
import ru.otus.spring.hw9.domain.Author;
import ru.otus.spring.hw9.exception.CannotUpdateException;
import ru.otus.spring.hw9.exception.NotFoundException;

import java.util.List;

//TODO: выделить интерфейсы для сервисов
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorDao authorDao;

    public List<Author> getAllAuthors() {
        return authorDao.getAll();
    }

    public Long getCountOfAuthors() {
        return authorDao.count();
    }

    public Author getAuthorByName(String authorName) {
        return authorDao.getByName(authorName);
    }

    public Author getAuthorById(Long authorId) {
        try {
            return authorDao.getById(authorId);
        } catch (EmptyResultDataAccessException e){
            throw new NotFoundException("Автор с id=%d не найден".formatted(authorId));
        }
    }

    public void addAuthor(String authorName) {
        Author author = Author.builder().name(authorName).build();
        authorDao.insert(author);
    }

    public void deleteAuthor(Long id) {
        authorDao.deleteById(id);

    }

    public void updateAuthor(Long id, String name) {
        if (id == null) {
            throw new CannotUpdateException("Book id should not be null");
        }
        authorDao.update(Author.builder().id(id).name(name).build());
    }

}
