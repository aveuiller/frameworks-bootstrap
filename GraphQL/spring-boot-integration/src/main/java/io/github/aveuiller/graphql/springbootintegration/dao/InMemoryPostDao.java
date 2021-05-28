package io.github.aveuiller.graphql.springbootintegration.dao;

import io.github.aveuiller.graphql.springbootintegration.models.Author;
import io.github.aveuiller.graphql.springbootintegration.models.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InMemoryPostDao implements PostDao {
    List<Post> posts = new ArrayList<>();
    Map<Author, List<Post>> byAuthor = new HashMap<>();

    @Override
    public Post write(Post post) {
        this.posts.add(0, post);
        this.byAuthor.computeIfAbsent(post.getAuthor(), k -> new ArrayList<>()).add(post);
        post.setId(String.valueOf(posts.size()));
        return post;
    }

    @Override
    public List<Post> fetchRecent(int count, int offset) {
        return posts.stream().skip(offset).limit(count).collect(Collectors.toList());
    }

    @Override
    public List<Post> forAuthor(Author author) {
        return byAuthor.get(author);
    }

    @Override
    public Optional<Post> byId(String id) {
        for (Post post : posts) {
            if (id.equals(post.getId())) {
                return Optional.of(post);
            }
        }
        return Optional.empty();
    }
}
