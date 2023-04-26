package ru.otus.spring.hw9.dao.impl;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.hw9.dao.GenreDao;
import ru.otus.spring.hw9.domain.Genre;
import ru.otus.spring.hw9.exception.CannotInsertException;
import ru.otus.spring.hw9.exception.CannotUpdateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        Integer count = jdbc.queryForObject("select count(*) from genre", Collections.emptyMap(), Integer.class);
        return count == null ? 0L : count;
    }

    @Override
    public void insert(Genre genre) {
        var existingGenre = this.getByName(genre.getGenreName());
        if (existingGenre != null) {
            throw new CannotInsertException("Genre with name %s already exist".formatted(genre.getGenreName()));
        }
        jdbc.update("insert into genre (genre_name) values (:name)",
                Map.of("name", genre.getGenreName()));
    }

    @Override
    public Genre getById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject(
                "select id, genre_name from genre where id = :id", params, new GenreMapper()
        );
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, genre_name from genre", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from genre where id = :id", params
        );
    }

    @Override
    public Genre getByName(String name) {
        try {
            Map<String, Object> params = Collections.singletonMap("name", name);
            return jdbc.queryForObject(
                    "select id, genre_name from genre where genre_name = :name", params, new GenreMapper()
            );
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void update(Genre genre) {
        try {
            this.getById(genre.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new CannotUpdateException("Genre with id %s not found".formatted(genre.getId()));
        }
        jdbc.update("update genre set genre_name = :name where id = :id",
                Map.of("id", genre.getId(),
                        "name", genre.getGenreName()
                ));

    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("genre_name");
            return new Genre(id, name);
        }
    }
}
