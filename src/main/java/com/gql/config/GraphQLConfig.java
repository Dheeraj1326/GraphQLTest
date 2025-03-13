package com.gql.config;

import graphql.schema.idl.TypeRuntimeWiring;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StreamUtils;
import com.gql.service.AuthorService;
import com.gql.service.BookService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class GraphQLConfig {

    private final ResourceLoader resourceLoader;
    private final BookService bookService;
    private final AuthorService authorService;

    public GraphQLConfig(ResourceLoader resourceLoader, BookService bookService, AuthorService authorService) {
        this.resourceLoader = resourceLoader;
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @Bean
    public GraphQLSchema graphQLSchema() throws IOException {
        Resource schemaResource = resourceLoader.getResource("classpath:graphql/schema.graphqls");

        if (!schemaResource.exists()) {
            throw new RuntimeException("GraphQL schema file not found!");
        }

        String sdl = StreamUtils.copyToString(schemaResource.getInputStream(), StandardCharsets.UTF_8);
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring wiring = buildWiring();
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    }

    private RuntimeWiring buildWiring() {
        return RuntimeWiring.newRuntimeWiring()
            .type(TypeRuntimeWiring.newTypeWiring("Query")
                .dataFetcher("findAllBooks", env -> bookService.getAllBook())
                .dataFetcher("findBookById", env -> bookService.findBookById(Long.parseLong(env.getArgument("id"))))
                .dataFetcher("findAllAuthors", env -> authorService.getAllAuthor())
                .dataFetcher("findAuthorById", env -> authorService.findAuthorById(Long.parseLong(env.getArgument("id"))))
            )
            .type(TypeRuntimeWiring.newTypeWiring("Mutation")
                .dataFetcher("createAuthor", env -> {
                    String name = env.getArgument("name");
                    if (name == null || name.trim().isEmpty()) {
                        throw new RuntimeException("Author name cannot be null or empty.");
                    }
                    return authorService.createAuthor(name);
                })
                .dataFetcher("updateAuthor", env -> authorService.updateAuthor(
                    Long.parseLong(env.getArgument("id")),
                    env.getArgument("name"))
                )
                .dataFetcher("deleteAuthor", env -> authorService.deleteAuthor(
                    Long.parseLong(env.getArgument("id"))
                ))
                .dataFetcher("createBook", env -> bookService.create(
                    env.getArgument("title"),
                    env.getArgument("isbn"),
                    Long.parseLong(env.getArgument("authorId"))
                ))
                .dataFetcher("updateBook", env -> bookService.updateBook(
                    Long.parseLong(env.getArgument("id")),
                    env.getArgument("title"),
                    env.getArgument("isbn"),
                    Long.parseLong(env.getArgument("authorId"))  // Fix: Ensure 'authorId' matches schema
                ))
                .dataFetcher("deleteBook", env -> bookService.deleteBook(Long.parseLong(env.getArgument("id"))))
            )
            .type(TypeRuntimeWiring.newTypeWiring("Book"))  // Ensure Book is registered
            .type(TypeRuntimeWiring.newTypeWiring("Author")) // Ensure Author is registered
            .build();
    }

}

