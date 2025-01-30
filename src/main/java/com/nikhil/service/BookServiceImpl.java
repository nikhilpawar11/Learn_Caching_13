package com.nikhil.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nikhil.entity.Book;
import com.nikhil.exception.ResourceNotFoundException;
import com.nikhil.helper.PegiableResponse;
import com.nikhil.helper.PeginationHelper;
import com.nikhil.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookRepository bookRepo;

	@Override
	public Book createBook(Book book) {
		
		// set book id
		book.setBookId(UUID.randomUUID().toString());
		
		Book saveBook = bookRepo.save(book);
		
		return saveBook;
	}

	@Override
	public Book updateBook(Book book, String bookId) {
		
		Book bookById = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with given Id "+bookId));
		
		bookById.setBookName(book.getBookName());
		
		Book updatedBook = bookRepo.save(bookById);
		
		return updatedBook;
	}

	@Override
	public void deleteBook(String bookId) {
		
		Book bookById = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with given Id "+bookId));
		
		bookRepo.delete(bookById);
		
	}

	@Override
	public Book getBookById(String bookId) {
		
		Book bookById = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with given Id "+bookId));
		
		return bookById;
	}

	@Override
	public List<Book> getAllBook() {
		
		List<Book> allBooks = bookRepo.findAll();
		
		return allBooks;
	}

	@Override
	public PegiableResponse<Book> getAllBooksWithPegination (int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Book> page = bookRepo.findAll(pageable);
		
		PegiableResponse<Book> peginationResponse = PeginationHelper.getPeginationResponse(page);
		
		return peginationResponse;
	}

}
