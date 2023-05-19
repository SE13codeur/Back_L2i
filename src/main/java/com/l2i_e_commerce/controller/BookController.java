package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items/books")
public class BookController extends GenericItemController<Book> {

	private final ItemService<Book, Long> bookService;

	public BookController(ItemService<Book, Long> bookService) {
		super(bookService);
		this.bookService = bookService;
	}

	@Override
	@PostMapping
	public ResponseEntity<Book> createItem(@RequestBody Book book) {
		try {
			Book createdBook = bookService.save(book);
			return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
