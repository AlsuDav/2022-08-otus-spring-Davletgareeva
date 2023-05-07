package ru.otus.spring.hw11.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@RequiredArgsConstructor
@Builder
@Setter
public class Author {
    private final Long id;
    private final String name;

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (name == null ? 0 : name.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Author otherAuthor = (Author) obj;
        if (this.id == null || otherAuthor.id == null)
            return this.name.equals(otherAuthor.name);
        return this.id.equals(otherAuthor.id) && this.name.equals(otherAuthor.name);
    }

}
