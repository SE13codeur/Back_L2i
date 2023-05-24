package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items/books")
public class BookController {

	private final BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping
	public ResponseEntity<Book> createItem(@RequestBody Book book) {
		try {
			Book createdBook = bookService.save(book);
			return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

//	@GetMapping("/{page}/{size}")
//	public ResponseEntity<List<Book>> listBooks(@PathVariable int page, @PathVariable int size) {
//		Page<Book> bookPage = bookService.findAll(page, size);
//		if (bookPage.isEmpty()) {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<>(bookPage.getContent(), HttpStatus.OK);
//	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Book>> listBooks() {
		List<Book> books = bookService.findAll();
		return new ResponseEntity<>(books, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Book>> detailsBook(@PathVariable Long id) {
		Optional<Book> book = bookService.findById(id);
		return new ResponseEntity<>(book, HttpStatus.OK);
	}


}
