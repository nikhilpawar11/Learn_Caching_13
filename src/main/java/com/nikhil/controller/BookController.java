package com.nikhil.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.entity.Book;
import com.nikhil.exception.ApiResponseMessage;
import com.nikhil.helper.PegiableResponse;
import com.nikhil.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@PostMapping("/saveBook")
	public ResponseEntity<Book> saveBook(@RequestBody Book book){
		
		Book saveBook = bookService.createBook(book);
		
		return new ResponseEntity<>(saveBook, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/updateBook/{bookId}")
	public ResponseEntity<Book> updateBook(@RequestBody Book book, @PathVariable String bookId){
		
		Book updatedBook = bookService.updateBook(book, bookId);
		
		return new ResponseEntity<>(updatedBook, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteBook/{bookId}")
	public ResponseEntity<ApiResponseMessage> deleteBook(@PathVariable String bookId){
		
		bookService.deleteBook(bookId);
		
		ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("Book deleted succsessfully...!!!").success(true).status(HttpStatus.OK).build();
		
		return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
		
	}
	
	@GetMapping("/bookById/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable String bookId){
		
		Book bookById = bookService.getBookById(bookId);
		
		return new ResponseEntity<>(bookById, HttpStatus.OK);
		
	}
	
	@GetMapping("/allBook")
	public ResponseEntity<List<Book>> getAllBooks(){
		
		List<Book> allBook = bookService.getAllBook();
		
		return new ResponseEntity<>(allBook, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/getWithPegination")
	public ResponseEntity<PegiableResponse<Book>> getWithPegination(
			@RequestParam(name = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5", required = false) int pageSize,
			@RequestParam(name = "sortBy", defaultValue = "bookName", required = false) String sortBy,
			@RequestParam(name = "sortDir", defaultValue = "asc", required = false) String sortDir
			){
		
		PegiableResponse<Book> allBooksWithPegination = bookService.getAllBooksWithPegination(pageNumber, pageSize, sortBy, sortDir);
		
		return new ResponseEntity<>(allBooksWithPegination, HttpStatus.OK);
		
	}

}
