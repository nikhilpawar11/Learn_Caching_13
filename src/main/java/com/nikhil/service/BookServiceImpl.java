package com.nikhil.service;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
	
	Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

	@Override
	@CachePut(value = "book", key = "#book.bookId")
	public Book createBook(Book book) {
		
		// set book id
		book.setBookId(UUID.randomUUID().toString());
		
		Book saveBook = bookRepo.save(book);
		
		logger.info("Data Saved Successfully in DB");
		
		return saveBook;
	}

	@Override
	@CachePut(value = "book", key = "#bookId")
	public Book updateBook(Book book, String bookId) {
		
		Book bookById = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with given Id "+bookId));
		
		bookById.setBookName(book.getBookName());
		
		Book updatedBook = bookRepo.save(bookById);
		
		logger.info("Book Updated With Give BookId "+bookId);
		
		return updatedBook;
	}

	@Override
	@CacheEvict(value = "book", key = "#bookId")
	public void deleteBook(String bookId) {
		
		Book bookById = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with given Id "+bookId));
		
		bookRepo.delete(bookById);
		
		logger.info("Book Deleted Successfully With Given BookId "+bookId);
		
	}

	@Override
	@Cacheable(value = "book", key = "#bookId")
	public Book getBookById(String bookId) {
		
		logger.info("Fetching Book From Data-Base");
		
		Book bookById = bookRepo.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with given Id "+bookId));
		
		return bookById;
	}

	@Override
	@Cacheable(cacheNames = "book")
	public List<Book> getAllBook() {
		
		List<Book> allBooks = bookRepo.findAll();
		
		logger.info("Successfully Fetch All Books From DB");
		
		return allBooks;
	}

	@Override
	public PegiableResponse<Book> getAllBooksWithPegination (int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort = (sortBy.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		Page<Book> page = bookRepo.findAll(pageable);
		
		PegiableResponse<Book> peginationResponse = PeginationHelper.getPeginationResponse(page);
		
		logger.info("Data Fetch With Pegination");
		
		return peginationResponse;
	}

	
}

