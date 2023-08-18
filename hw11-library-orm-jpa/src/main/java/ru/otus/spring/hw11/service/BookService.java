package ru.otus.spring.hw11.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.otus.spring.hw11.repositories.BookRepository;
import ru.otus.spring.hw11.entity.Author;
import ru.otus.spring.hw11.entity.Book;
import ru.otus.spring.hw11.entity.Genre;
import ru.otus.spring.hw11.exception.CannotUpdateException;
import ru.otus.spring.hw11.exception.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book getBookByName(String bookName) {
        return bookRepository.getByName(bookName);
    }

    public Book getBookById(Long bookId) {
        try {
            return bookRepository.getById(bookId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Книга с id=%d не найдена".formatted(bookId));
        }
    }

    public void addBook(String bookName, List<String> authorNames, List<String> genreNames) {

        Set<Author> authors = authorNames.stream().map(authorName -> Author.builder().name(authorName).build()).collect(Collectors.toSet());
        Set<Genre> genres = genreNames.stream().map(genreName -> Genre.builder().genreName(genreName).build()).collect(Collectors.toSet());

        Book book = Book.builder().bookName(bookName).authors(authors).genres(genres).build();
        bookRepository.insert(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);

    }

    public Long getCountOfBooks() {
        return bookRepository.count();
    }

    public void updateBook(Long id, String bookName, List<String> authorNames, List<String> genreNames) {

        if (id == null) {
            throw new CannotUpdateException("Book id should not be null");
        }

        Set<Author> authors = authorNames.stream().map(authorName -> Author.builder().name(authorName).build()).collect(Collectors.toSet());
        Set<Genre> genres = genreNames.stream().map(genreName -> Genre.builder().genreName(genreName).build()).collect(Collectors.toSet());

        Book book = Book.builder().id(id).bookName(bookName).authors(authors).genres(genres).build();
        bookRepository.update(book);
    }

}
