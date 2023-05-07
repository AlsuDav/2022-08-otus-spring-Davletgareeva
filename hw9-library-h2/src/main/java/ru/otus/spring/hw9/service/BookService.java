package ru.otus.spring.hw9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw9.dao.BookDao;
import ru.otus.spring.hw9.domain.Author;
import ru.otus.spring.hw9.domain.Book;
import ru.otus.spring.hw9.domain.Genre;
import ru.otus.spring.hw9.exception.CannotUpdateException;
import ru.otus.spring.hw9.exception.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookDao bookDao;

    public List<Book> getAllBooks() {
        return bookDao.getAll();
    }

    public Book getBookByName(String bookName) {
        return bookDao.getByName(bookName);
    }

    public Book getBookById(Long bookId) {
        try {
            return bookDao.getById(bookId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Книга с id=%d не найдена".formatted(bookId));
        }
    }

    public void addBook(String bookName, List<String> authorNames, List<String> genreNames) {

        Set<Author> authors = authorNames.stream().map(authorName -> Author.builder().name(authorName).build()).collect(Collectors.toSet());
        Set<Genre> genres = genreNames.stream().map(genreName -> Genre.builder().genreName(genreName).build()).collect(Collectors.toSet());

        Book book = Book.builder().bookName(bookName).authors(authors).genres(genres).build();
        bookDao.insert(book);
    }

    public void deleteBook(Long id) {
        bookDao.deleteById(id);

    }

    public Long getCountOfBooks() {
        return bookDao.count();
    }

    public void updateBook(Long id, String bookName, List<String> authorNames, List<String> genreNames) {

        if (id == null) {
            throw new CannotUpdateException("Book id should not be null");
        }

        Set<Author> authors = authorNames.stream().map(authorName -> Author.builder().name(authorName).build()).collect(Collectors.toSet());
        Set<Genre> genres = genreNames.stream().map(genreName -> Genre.builder().genreName(genreName).build()).collect(Collectors.toSet());

        Book book = Book.builder().id(id).bookName(bookName).authors(authors).genres(genres).build();
        bookDao.update(book);
    }

}
