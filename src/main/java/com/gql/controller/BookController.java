package com.gql.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.gql.model.Book;
import com.gql.service.BookService;

@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @QueryMapping
    public List<Book> findAllBooks() {
        return bookService.getAllBook();
    }

    @QueryMapping
    public Book findBookById(@Argument Long id) {
        return bookService.findBookById(id);
    }

    @MutationMapping
    public Book createBook(@Argument String title, @Argument Long authorId, @Argument String isbn) {
        return bookService.create(title, isbn, authorId);
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument String title, @Argument Long authorId, @Argument String isbn) {
        return bookService.updateBook(id, title, isbn, authorId);
    }

    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        return bookService.deleteBook(id);
    }
}
