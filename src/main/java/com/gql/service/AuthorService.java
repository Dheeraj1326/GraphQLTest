package com.gql.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gql.model.Author;
import com.gql.repository.AuthorRepository;
import com.gql.service.interfaces.AuthorServiceInterface;

@Service
public class AuthorService implements AuthorServiceInterface {

	private AuthorRepository authorRepo;

	public AuthorService(AuthorRepository authorRepo) {
		this.authorRepo = authorRepo;
	}

	@Override
	public List<Author> getAllAuthor() {
		return authorRepo.findAll();
	}

	@Override
	public Author createAuthor(String name) {
		if(name == null || name.trim().isEmpty()) throw new RuntimeException("Author name cannot be null or empty");
		
		Author author = new Author();
		author.setName(name);
		Author save = authorRepo.save(author);
		
		if(save == null) throw new RuntimeException("Failed To Save Author.");
		
		return save;
	}

	@Override
	public Author findAuthorById(Long id) {
		return authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author Not Found"));
	}

	@Override
	public Author updateAuthor(Long id, String name) {
		Author author = authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author Not Found"));
		if (name != null) {
			author.setName(name);
		} else {
			new RuntimeException("Name is required.");
		}
		return authorRepo.save(author);
	}

	@Override
	public boolean deleteAuthor(Long id) {
		Author author = authorRepo.findById(id).orElseThrow(() -> new RuntimeException("Author Not Found"));
		authorRepo.delete(author);
		return true;
	}

}
