package com.nikhil.service;

import java.util.List;

import com.nikhil.entity.Book;
import com.nikhil.helper.PegiableResponse;

public interface BookService {
	
	// add 
	public Book createBook(Book book);
	
	// update
	public Book updateBook(Book book, String bookId);
	
	// delete
	public void deleteBook(String bookId);
	
	// get by id
	public Book getBookById(String bookId);
	
	// get all book
	List<Book> getAllBook();
	
	// get all books with pagination
	PegiableResponse<Book> getAllBooksWithPegination (int pageNumber, int pageSize, String sortBy, String sortDir);
	
	
}
