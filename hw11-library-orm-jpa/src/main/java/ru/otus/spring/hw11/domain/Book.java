package ru.otus.spring.hw11.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "author_books", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name="author_id"))
    private List<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name="genre_id"))
    private List<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "book_id")
    private List<Comment> comments;


    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (bookName == null ? 0 : bookName.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Book otherBook = (Book) obj;
        if (this.id == null || otherBook.id == null)
            return this.bookName.equals(otherBook.bookName);
        return this.id.equals(otherBook.id) && this.bookName.equals(otherBook.bookName);
    }

}
