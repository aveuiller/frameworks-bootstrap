package io.github.aveuiller.graphql.springbootintegration;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import io.github.aveuiller.graphql.springbootintegration.dao.PostDao;
import io.github.aveuiller.graphql.springbootintegration.models.Author;
import io.github.aveuiller.graphql.springbootintegration.models.MutationResolver;
import io.github.aveuiller.graphql.springbootintegration.models.Post;
import io.github.aveuiller.graphql.springbootintegration.models.WritePostMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


/**
 * {@link GraphQLMutationResolver} is used by Spring Boot to detect the GraphQL available queries.
 * {@link MutationResolver} and {@link WritePostMutationResolver} are giving the methods to implement on this resolver.
 */
@Component
public class Mutation implements GraphQLMutationResolver, WritePostMutationResolver, MutationResolver {

    @Autowired
    PostDao dao;

    @Override
    public @NotNull Post writePost(@NotNull String title, @NotNull String text, String category) throws Exception {
        Post.Builder builder = new Post.Builder();
        Author an_author = new Author.Builder().setId("1").setName("an author").build();
        builder.setTitle(title)
                .setText(text)
                .setCategory(category)
                // TODO: The following are not sent through the builder
                .setAuthor(an_author);
        return dao.write(builder.build());
    }
}
