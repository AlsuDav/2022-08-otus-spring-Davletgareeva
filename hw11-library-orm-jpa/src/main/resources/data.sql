insert into genre(id, genre_name)
values (1, 'Action and Adventure'),
--     The Hobbit by J.R.R. Tolkien
-- The Three Musketeers by Alexandre Dumas
-- Life of Pi by Yann Martel
       (2, 'Anthology'),
-- The Poets Laureate Anthology by Elizabeth Hun Schmidt
       (3, 'Classic'),
--     To Kill a Mockingbird by Harper Lee
-- 1984 by George Orwell
-- Romeo and Juliet by William Shakespeare
       (4, 'Crime and Detective'),
--     Sherlock Holmes by Arthur Conan Doyle
-- And There Were None by Agatha Christie
-- Murder on The Orient Express by Agatha Christie
       (5, 'Drama');
--     Hamlet by William Shakespeare
-- Waiting For Godot by Samuel Beckett

insert into author(id, name)
values (1, 'J.R.R. Tolkien'),
       (2, 'Alexandre Dumas'),
       (3, 'Yann Martel'),
       (4, 'Elizabeth Hun Schmidt'),
       (5, 'Harper Lee'),
       (6, 'George Orwell'),
       (7, 'William Shakespeare'),
       (8, 'Arthur Conan Doyle'),
       (9, 'Agatha Christie'),
       (10, 'Samuel Beckett');

insert into book(id, book_name)
values (1, 'The Hobbit'),
       (2, 'The Three Musketeers'),
       (3, 'Life of Pi'),
       (4, 'The Poets Laureate Anthology'),
       (5, 'To Kill a Mockingbird'),
       (6, '1984'),
       (7, 'Romeo and Juliet'),
       (8, 'Sherlock Holmes'),
       (9, 'And There Were None'),
       (10, 'Murder on The Orient Express'),
       (11, 'Hamlet'),
       (12, 'Waiting For Godot');

insert into book_genres(book_id, genre_id)
values
    (1, 1), (2, 1), (3, 1), (4, 2), (5, 3), (6, 3), (7, 3), (8, 4), (9, 4), (10, 4), (11, 5), (12, 5);

insert into author_books(author_id, book_id)
values
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (7, 11),
    (8, 8),
    (9, 9),
    (9, 10),
    (10, 12);