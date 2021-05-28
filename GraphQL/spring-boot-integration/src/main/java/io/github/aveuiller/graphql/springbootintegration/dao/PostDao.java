package io.github.aveuiller.graphql.springbootintegration.dao;

import io.github.aveuiller.graphql.springbootintegration.models.Author;
import io.github.aveuiller.graphql.springbootintegration.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostDao {
    Post write(Post post);

    List<Post> fetchRecent(int count, int page);

    List<Post> forAuthor(Author author);

    Optional<Post> byId(String id);
}
