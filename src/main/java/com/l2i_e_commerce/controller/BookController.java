package com.l2i_e_commerce.controller;

import com.l2i_e_commerce.model.Book;
import com.l2i_e_commerce.service.*;

/*import org.springframework.http.*;
*/import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/items/books", method = RequestMethod.GET)
public class BookController extends GenericItemController<Book> {
	public BookController(MeiliSearchGenericService<Book> bookService) {
        super(bookService);
    }
}
	/*
	 * @Autowired private BookService bookService;
	 * 
	 * @PostMapping public ResponseEntity<Book> createBook(@RequestBody Book book) {
	 * return
	 * ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(book)); }
	 * 
	 * @PutMapping("/{id}") public ResponseEntity<Book> updateBook(@PathVariable
	 * Long id, @RequestBody Book book) { book.setId(id); return
	 * ResponseEntity.ok(bookService.update(book)); }
	 * 
	 * @DeleteMapping("/{id}") public ResponseEntity<Void> deleteBook(@PathVariable
	 * Long id) { bookService.deleteById(id); return
	 * ResponseEntity.noContent().build(); }
	 */





