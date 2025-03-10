package com.gql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gql.model.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
