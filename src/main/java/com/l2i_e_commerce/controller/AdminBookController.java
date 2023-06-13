package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/items/books")
public class AdminBookController {

    private final BookService bookService;

    @Autowired
    public AdminBookController(BookService bookService) {
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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> bookData = bookService.findById(id);

        if (bookData.isPresent()) {
            Book book = bookData.get();

            book.setIsbn13(bookDetails.getIsbn13());
            book.setImageUrl(bookDetails.getImageUrl());
            book.setTitle(bookDetails.getTitle());
            book.setSubtitle(bookDetails.getSubtitle());
            book.setDescription(bookDetails.getDescription());
            book.setRegularPrice(bookDetails.getRegularPrice());
            book.setRating(bookDetails.getRating());
            book.setQuantityInStock(bookDetails.getQuantityInStock());
            book.setTotalSales(bookDetails.getTotalSales());
            book.setAuthors(bookDetails.getAuthors());
            book.setEditor(bookDetails.getEditor());
            book.setCategory(bookDetails.getCategory());
            book.setPages(bookDetails.getPages());
            book.setYear(bookDetails.getYear());
            book.setLanguage(bookDetails.getLanguage());
            book.setVersion(bookDetails.getVersion());

            return new ResponseEntity<>(bookService.save(book), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

