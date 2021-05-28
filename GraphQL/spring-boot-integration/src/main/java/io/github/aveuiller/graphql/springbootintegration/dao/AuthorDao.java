package io.github.aveuiller.graphql.springbootintegration.dao;

import io.github.aveuiller.graphql.springbootintegration.models.Author;

import java.util.Optional;

public interface AuthorDao {
    Optional<Author> getAuthor(String id);

    Author addAuthor(Author author);
}
