DROP TABLE IF EXISTS genre;
CREATE TABLE genre(
    id BIGINT PRIMARY KEY,
    genre_name VARCHAR(255)
);

DROP TABLE IF EXISTS author;
CREATE TABLE author(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book(
    id BIGINT PRIMARY KEY,
    book_name VARCHAR(255),
    author_id BIGINT,
    genre_id BIGINT,
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (genre_id) REFERENCES genre(id)
);

-- DROP TABLE IF EXISTS book_genres;
-- CREATE TABLE book_genres(
--     book_id BIGINT,
--     genre_id BIGINT,
--     FOREIGN KEY (book_id) REFERENCES book(id),
--     FOREIGN KEY (genre_id) REFERENCES genre(id)
-- );

DROP TABLE IF EXISTS author_books;
CREATE TABLE author_books(
    author_id BIGINT,
    book_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (author_id) REFERENCES author(id)
);
