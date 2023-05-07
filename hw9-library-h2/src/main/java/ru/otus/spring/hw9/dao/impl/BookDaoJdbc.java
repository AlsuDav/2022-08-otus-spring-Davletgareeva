package ru.otus.spring.hw9.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw9.dao.AuthorDao;
import ru.otus.spring.hw9.dao.BookDao;
import ru.otus.spring.hw9.dao.GenreDao;
import ru.otus.spring.hw9.domain.Author;
import ru.otus.spring.hw9.domain.Book;
import ru.otus.spring.hw9.domain.Genre;
import ru.otus.spring.hw9.exception.CannotInsertException;
import ru.otus.spring.hw9.exception.CannotUpdateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class BookDaoJdbc implements BookDao {
    //TODO:переставить методы по порядку: public, protected, private
    private final NamedParameterJdbcOperations jdbc;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc, AuthorDao authorDao, GenreDao genreDao) {
        this.jdbc = jdbc;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public Long count() {
        Integer count = jdbc.queryForObject("select count(*) from book", Collections.emptyMap(), Integer.class);
        return count == null ? 0L : count;
    }

    @Override
    public void insert(Book book) {

        var existingBook = this.getByName(book.getBookName());
        if (existingBook != null) {
            throw new CannotInsertException("Book with name %s already exist".formatted(book.getBookName()));
        }


        jdbc.update("insert into book (book_name) values (:name)",
                Map.of("name", book.getBookName()
                ));


        var bookFromDb = this.getByName(book.getBookName());

        insertAuthorForBook(book, bookFromDb.getId());
        insertGenreForBook(book, bookFromDb.getId());

    }

    private void insertGenreForBook(Book book, Long bookId) {
        //TODO: делать инсерт с возвратом id
        for (var genre : book.getGenres()) {
            var genreName = genre.getGenreName();
            var genreFromDb = genreDao.getByName(genreName);
            if (genreFromDb == null) {
                genreDao.insert(Genre.builder().genreName(genreName).build());
                genreFromDb = genreDao.getByName(genreName);
            }
            jdbc.update("insert into book_genres (book_id, genre_id) values (:bookId, :genreId)",
                    Map.of("genreId", genreFromDb.getId(),
                            "bookId", bookId
                    ));
        }
    }

    private void insertAuthorForBook(Book book, Long bookId) {
        for (var author : book.getAuthors()) {
            var authorName = author.getName();
            var authorFromDb = authorDao.getByName(authorName);
            if (authorFromDb == null) {
                authorDao.insert(Author.builder().name(authorName).build());
                authorFromDb = authorDao.getByName(authorName);
            }
            jdbc.update("insert into author_books (author_id, book_id) values (:authorId, :bookId)",
                    Map.of("authorId", authorFromDb.getId(),
                            "bookId", bookId
                    ));
        }
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        try {
            return jdbc.queryForObject("""
                            with current_book as (select id as book_id, book_name
                                                      from book
                                                      where id = :id),
                                     authors as (select book_id, author_id, name as author_name
                                                 from (select author_id, book_id
                                                       from author_books
                                                       where book_id = :id) ab
                                                          left join author
                                                                    on ab.author_id = author.id),
                                     genres as (select book_id, genre_id, genre_name
                                                from (select book_id, genre_id
                                                      from book_genres
                                                      where book_id = :id) bg
                                                         left join genre
                                                                   on bg.genre_id = genre.id)
                                select cb.book_id, cb.book_name, author_id, author_name, genre_id, genre_name
                                from current_book cb
                                         left join authors
                                                   on cb.book_id = authors.book_id
                                         left join genres
                                                   on cb.book_id = genres.book_id
                            """
                    , params, new BookMapper()
            );
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Book getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("authorName", name);
        try {
            return jdbc.queryForObject("""
                            with current_book as (select id as book_id, book_name
                                                         from book
                                                       where book_name like :authorName),
                                      authors as (select book_id, author_id, name as author_name
                                                  from (select author_id, book_id
                                                        from author_books
                                                        where book_id = (select book_id from current_book)) ab
                                                           left join author
                                                                     on ab.author_id = author.id),
                                      genres as (select book_id, genre_id, genre_name
                                                 from (select book_id, genre_id
                                                       from book_genres
                                                       where book_id = (select book_id from current_book)) bg
                                                          left join genre
                                                                    on bg.genre_id = genre.id)
                                 select cb.book_id, cb.book_name, author_id, author_name, genre_id, genre_name
                                 from current_book cb
                                          left join authors
                                                    on cb.book_id = authors.book_id
                                          left join genres
                                                    on cb.book_id = genres.book_id
                            """
                    , params, new BookMapper());
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }

    }

    @Override
    public List<Book> getAll() {
        return jdbc.query
                ("""
                                with authors as (select book_id, author_id, name as author_name
                                                 from author_books ab
                                                          left join author
                                                                    on ab.author_id = author.id),
                                     genres as (select book_id, genre_id, genre_name
                                                from book_genres bg
                                                         left join genre
                                                                   on bg.genre_id = genre.id)
                                select b.id as book_id, b.book_name, author_id, author_name, genre_id, genre_name
                                from book b
                                         left join authors
                                                   on b.id = authors.book_id
                                         left join genres
                                                   on b.id = genres.book_id;
                                """
                        , new BookListExtractor());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from book where id = :id", params
        );
    }

    @Override
    public void update(Book book) {

        var existingBook = this.getById(book.getId());
        if (existingBook == null) {
            throw new CannotUpdateException("Book with id %s not found".formatted(book.getId()));
        }
        jdbc.update("update book set book_name = :name where id = :id",
                Map.of("id", book.getId(),
                        "name", book.getBookName()
                ));
        deleteOldRelationsForBook(book);
        insertGenreForBook(book, book.getId());
        insertAuthorForBook(book, book.getId());

    }

    private void deleteOldRelationsForBook(Book book) {
        Map<String, Object> params = Collections.singletonMap("id", book.getId());
        jdbc.update(
                """ 
                        delete from book_genres where book_id = :id;
                        delete from author_books where book_id = :id;
                        """, params
        );
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int i) throws SQLException {
            Book currentBook = null;
            do {
                if (currentBook == null) {
                    long bookId = rs.getLong("book_id");
                    String bookName = rs.getString("book_name");
                    currentBook = Book.builder()
                            .id(bookId)
                            .bookName(bookName)
                            .authors(new HashSet<>())
                            .genres(new HashSet<>())
                            .build();
                }
                if (rs.getLong("author_id") != 0 && rs.getString("author_name") != null) {
                    currentBook.getAuthors().add(Author.builder()
                            .id(rs.getLong("author_id"))
                            .name(rs.getString("author_name"))
                            .build());
                }
                if (rs.getLong("genre_id") != 0 && rs.getString("genre_name") != null) {
                    currentBook.getGenres().add(Genre.builder()
                            .id(rs.getLong("genre_id"))
                            .genreName(rs.getString("genre_name"))
                            .build());
                }
            } while (rs.next());
            return currentBook;
        }
    }

    private static class BookListExtractor implements ResultSetExtractor<List<Book>> {
        HashMap<Long, Book> currentBooks = new HashMap<>();

        @Override
        public List<Book> extractData(ResultSet rs) throws SQLException, DataAccessException {
            while (rs.next()) {

                long bookId = rs.getLong("book_id");
                String bookName = rs.getString("book_name");
                Book currentBook;
                if (currentBooks.containsKey(bookId)) {
                    currentBook = currentBooks.get(bookId);
                } else {
                    currentBook = Book.builder()
                            .id(bookId)
                            .bookName(bookName)
                            .authors(new HashSet<>())
                            .genres(new HashSet<>())
                            .build();

                    currentBooks.put(bookId, currentBook);
                }
                currentBook.getAuthors().add(Author.builder()
                        .id(rs.getLong("author_id"))
                        .name(rs.getString("author_name"))
                        .build());
                currentBook.getGenres().add(Genre.builder()
                        .id(rs.getLong("genre_id"))
                        .genreName(rs.getString("genre_name"))
                        .build());
            }
            return new ArrayList<>(currentBooks.values());
        }
    }
}
