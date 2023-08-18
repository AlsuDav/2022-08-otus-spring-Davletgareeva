package ru.otus.spring.hw11.repositories.impl;

import jakarta.persistence.*;
import ru.otus.spring.hw11.repositories.AuthorRepository;
import ru.otus.spring.hw11.entity.Author;
import ru.otus.spring.hw11.exception.CannotUpdateException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Author insert(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> getAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("otus-student-avatars-entity-graph");
        TypedQuery<OtusStudent> query = em.createQuery("select s from OtusStudent s join fetch s.emails", OtusStudent.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public List<OtusStudent> findByName(String name) {
        TypedQuery<OtusStudent> query = em.createQuery("select s " +
                        "from OtusStudent s " +
                        "where s.name = :name",
                OtusStudent.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void updateNameById(long id, String name) {
        Query query = em.createQuery("update OtusStudent s " +
                "set s.name = :name " +
                "where s.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete " +
                "from OtusStudent s " +
                "where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
    }
    @Override
    public Long count() {
        Long count = jdbc.queryForObject("select count(*) from author", Collections.emptyMap(), Long.class);
        return count == null ? 0L : count;
    }

//    @Override
//    public void insert(Author author) {
//        //TODO:вынести проверки в бизнес логику
//        var existingAuthor = this.getByName(author.getName());
//        if (existingAuthor != null) {
//            throw new CannotInsertException("Author with name %s already exist".formatted(author.getName()));
//        }
//        jdbc.update("insert into author (name) values (:name)",
//                Map.of("name", author.getName()));
//    }

//    @Override
//    public Author getById(long id) {
//        Map<String, Object> params = Collections.singletonMap("id", id);
//
//        return jdbc.queryForObject(
//                "select id, name from author where id = :id", params, new AuthorMapper()
//        );
//
//    }

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
