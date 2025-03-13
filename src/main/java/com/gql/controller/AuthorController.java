package com.gql.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gql.model.Author;
import com.gql.service.AuthorService;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @QueryMapping
    public List<Author> findAllAuthors() {
        return authorService.getAllAuthor();
    }

    @QueryMapping
    public Author findAuthorById(@Argument Long id) {
        return authorService.findAuthorById(id);
    }

    @MutationMapping
    public Author createAuthor(@Argument String name) {
        return authorService.createAuthor(name);
    }

    @MutationMapping
    public Author updateAuthor(@Argument Long id, @Argument String name) {
        return authorService.updateAuthor(id, name);
    }

    @MutationMapping
    public boolean deleteAuthor(@Argument Long id) {
        return authorService.deleteAuthor(id);
    }
}
