package com.gql.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gql.model.Author;
import com.gql.model.Book;
import com.gql.repository.AuthorRepository;
import com.gql.repository.BookRepository;
import com.gql.service.interfaces.BookServiceInterface;

@Service
public class BookService implements BookServiceInterface{
	
	private BookRepository bookRepo;
	private AuthorRepository authorRepo;
	
	public BookService(BookRepository bookRepo, AuthorRepository authorRepo) {
		this.bookRepo = bookRepo;
		this.authorRepo = authorRepo;
	}

	@Override
	public List<Book> getAllBook() {
		return bookRepo.findAll();
	}

	@Override
	public Book create(String title, String isbn, Long authorId) {
		Author author = authorRepo.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found."));
		Book book = new Book();
		if(title != null) {book.setTitle(title);} 	else {new RuntimeException("Title of Book is required.");}
		if(isbn != null){book.setIsbn(isbn);}		else {new RuntimeException("ISBN of book is required.");}	
		if(author != null) {book.setAuthor(author);}else {new RuntimeException("Author not found.");}
		return bookRepo.save(book);
	}

	@Override
	public Book findBookById(Long id) {
		return bookRepo.findById(id).orElseThrow(()-> new RuntimeException("Book not found"));
	}

	@Override
	public Book updateBook(Long id, String title, String isbn, Long authorId) {
		Author author = authorRepo.findById(authorId).orElseThrow(()-> new RuntimeException("Author not found"));
		Book book = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
		
		if(title != null) {book.setTitle(title);} 	else {new RuntimeException("Title of Book is required.");}
		if(isbn != null){book.setIsbn(isbn);}		else {new RuntimeException("ISBN of book is required.");}	
		if(author != null) {book.setAuthor(author);}else {new RuntimeException("Author not found.");}
		return bookRepo.save(book);
	}

	@Override
	public boolean deleteBook(Long id) {
		Book book = bookRepo.findById(id).orElseThrow(() -> new RuntimeException("Book Not Found."));
		bookRepo.delete(book);
		return true;
	}

}
