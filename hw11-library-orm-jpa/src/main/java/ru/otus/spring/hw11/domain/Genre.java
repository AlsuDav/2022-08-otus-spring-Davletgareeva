package ru.otus.spring.hw11.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class Genre {
    private final Long id;
    private final String genreName;

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (genreName == null ? 0 : genreName.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        Genre otherGenre = (Genre) obj;
        if (this.id == null || otherGenre.id == null)
            return this.genreName.equals(otherGenre.genreName);
        return this.id.equals(otherGenre.id) && this.genreName.equals(otherGenre.genreName);
    }

}
