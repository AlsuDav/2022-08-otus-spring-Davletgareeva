package ru.otus.spring.hw9.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw9.dao.BookDao;
import ru.otus.spring.hw9.domain.Author;
import ru.otus.spring.hw9.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public int count() {
        Integer count = jdbc.queryForObject("select count(*) from book", Collections.emptyMap(), Integer.class);
        return count == null ? 0 : count;
    }

    @Override
    public void insert(Book book) {
        jdbc.update("insert into book (id, book_name, author_id) values (:id, :name, :author_id)",
                Map.of("id", book.getId(),
                        "name", book.getBookName(),
                        "author_id", book.getAuthor().getId()));
    }

    @Override
    public Book getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("""
                select id as book_id,
                        book_name as book_name
                         from book where id = :id
                left join author on book.author_id = author.id
                """
                , params, new BookMapper()
        );
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select id, name from author", new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from author where id = :id", params
        );
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("book_id");
            String name = resultSet.getString("book_name");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("author_name");
            return new Book(id, name, new Author(authorId, authorName));
        }
    }
}
