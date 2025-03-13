package com.gql.service.interfaces;

import java.util.List;

import com.gql.model.Book;

public interface BookServiceInterface {

	public List<Book> getAllBook();
	
	public Book create(String title, String isbn, Long authorId);
	
	public Book findBookById(Long id);
	
	public Book updateBook(Long id, String title, String isbn, Long authorId);
	
	public boolean deleteBook(Long id);
}
