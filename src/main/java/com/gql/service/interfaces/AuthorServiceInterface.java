package com.gql.service.interfaces;

import java.util.List;

import com.gql.model.Author;

public interface AuthorServiceInterface {
	public List<Author> getAllAuthor();
	
	public Author createAuthor(String name);
	
	public Author findAuthorById(Long id);
	
	public Author updateAuthor(Long id, String name);
	
	public boolean deleteAuthor(Long id);
}
