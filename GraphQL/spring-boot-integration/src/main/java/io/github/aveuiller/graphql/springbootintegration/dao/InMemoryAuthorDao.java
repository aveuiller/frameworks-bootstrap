package io.github.aveuiller.graphql.springbootintegration.dao;

import io.github.aveuiller.graphql.springbootintegration.models.Author;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InMemoryAuthorDao implements AuthorDao {
    private final List<Author> authors = new ArrayList<>();

    public Optional<Author> getAuthor(String id) {
        return authors.stream().filter(author -> id.equals(author.getId())).findFirst();
    }

    @Override
    public Author addAuthor(Author author) {
        this.authors.add(author);
        author.setId(String.valueOf(authors.size()));
        return author;
    }
}