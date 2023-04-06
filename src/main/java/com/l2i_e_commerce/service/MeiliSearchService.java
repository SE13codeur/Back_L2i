package com.l2i_e_commerce.service;

import com.l2i_e_commerce.model.Book;
import java.util.List;

public interface MeiliSearchService {
    void addOrUpdateBook(Book book) throws Exception;
    void deleteBookById(String id) throws Exception;
    List<Book> searchBooks(String query, String filter) throws Exception;
}
