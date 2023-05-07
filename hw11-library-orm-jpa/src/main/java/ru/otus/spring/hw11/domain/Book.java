package ru.otus.spring.hw11.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Builder
public class Book {
    private final Long id;
    private final String bookName;
    private final Set<Author> authors;
    private final Set<Genre> genres;

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
