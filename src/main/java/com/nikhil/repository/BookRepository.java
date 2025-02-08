package com.nikhil.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhil.entity.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	
	
	
}
