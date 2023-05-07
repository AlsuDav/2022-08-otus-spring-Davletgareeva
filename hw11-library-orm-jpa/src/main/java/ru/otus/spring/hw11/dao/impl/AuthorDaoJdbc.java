package ru.otus.spring.hw11.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw11.dao.AuthorDao;
import ru.otus.spring.hw11.domain.Author;
import ru.otus.spring.hw11.exception.CannotInsertException;
import ru.otus.spring.hw11.exception.CannotUpdateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        Long count = jdbc.queryForObject("select count(*) from author", Collections.emptyMap(), Long.class);
        return count == null ? 0L : count;
    }

    @Override
    public void insert(Author author) {
        //TODO:вынести проверки в бизнес логику
        var existingAuthor = this.getByName(author.getName());
        if (existingAuthor != null) {
            throw new CannotInsertException("Author with name %s already exist".formatted(author.getName()));
        }
        jdbc.update("insert into author (name) values (:name)",
                Map.of("name", author.getName()));
    }

    @Override
    public Author getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        return jdbc.queryForObject(
                "select id, name from author where id = :id", params, new AuthorMapper()
        );

    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name from author", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from author where id = :id", params
        );
    }

    @Override
    public Author getByName(String name) {
        Map<String, Object> params = Collections.singletonMap("name", name);
        try {
            return jdbc.queryForObject(
                    "select id, name from author where name = :name", params, new AuthorMapper()
            );
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(Author author) {
        try {
            this.getById(author.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new CannotUpdateException("Author with id %s not found".formatted(author.getId()));
        }
        jdbc.update("update author set name = :name where id = :id",
                Map.of("id", author.getId(),
                        "name", author.getName()
                ));

    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
