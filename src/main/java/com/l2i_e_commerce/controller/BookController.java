package com.l2i_e_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.service.BookService;
import com.l2i_e_commerce.service.MeiliSearchServiceImpl;

@RestController
@RequestMapping("/api/books")
public class BookController {
	
	private MeiliSearchServiceImpl meiliSearchService;

	@Autowired
	public BookController(MeiliSearchServiceImpl meiliSearchService) {
	    this.meiliSearchService = meiliSearchService;
	}

    @Autowired
    private BookService bookService;
    
    // Search via Meilisearch
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam String query, @RequestParam(required = false) String filter) {
        try {
            List<Book> books = meiliSearchService.searchBooks(query, filter);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        return ResponseEntity.ok(bookService.update(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}



