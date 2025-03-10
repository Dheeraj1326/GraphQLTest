package com.gql.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gql.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{

}
