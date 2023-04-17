DROP TABLE IF EXISTS genre;
DROP SEQUENCE IF EXISTS genre_id_seq;
CREATE SEQUENCE genre_id_seq START WITH 20;
CREATE TABLE genre(
    id BIGINT PRIMARY KEY,
    genre_name VARCHAR(255)
);
ALTER TABLE genre ALTER COLUMN id SET DEFAULT nextval('genre_id_seq');


DROP SEQUENCE IF EXISTS author_id_seq;
CREATE SEQUENCE author_id_seq START WITH 20;
DROP TABLE IF EXISTS author;
CREATE TABLE author(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);
ALTER TABLE author ALTER COLUMN id SET DEFAULT nextval('author_id_seq');

DROP SEQUENCE IF EXISTS book_id_seq;
CREATE SEQUENCE book_id_seq START WITH 20;
DROP TABLE IF EXISTS book;
CREATE TABLE book(
    id BIGINT PRIMARY KEY,
    book_name VARCHAR(255)
);
ALTER TABLE book ALTER COLUMN id SET DEFAULT nextval('book_id_seq');

DROP TABLE IF EXISTS book_genres;
CREATE TABLE book_genres(
    book_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE ,
    FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS author_books;
CREATE TABLE author_books(
    author_id BIGSERIAL,
    book_id BIGSERIAL,
    FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE ,
    FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);
