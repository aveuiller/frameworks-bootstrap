package io.github.aveuiller.graphql.springbootintegration;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import io.github.aveuiller.graphql.springbootintegration.dao.PostDao;
import io.github.aveuiller.graphql.springbootintegration.models.Post;
import io.github.aveuiller.graphql.springbootintegration.models.QueryResolver;
import io.github.aveuiller.graphql.springbootintegration.models.RecentPostsQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * {@link GraphQLQueryResolver} is used by Spring Boot to detect the GraphQL available queries.
 * {@link QueryResolver} and {@link RecentPostsQueryResolver} are giving the methods to implement on this resolver.
 */
@Component
public class Query implements GraphQLQueryResolver, QueryResolver, RecentPostsQueryResolver {
    private static final Logger LOG = LoggerFactory.getLogger("Query");
    @Autowired
    PostDao dao;

    @Override
    public List<Post> recentPosts(Integer count, Integer offset) throws Exception {
        return dao.fetchRecent(count == null ? 10 : count, offset == null ? 0 : offset);
    }
}