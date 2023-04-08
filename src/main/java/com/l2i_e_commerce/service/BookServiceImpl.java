package com.l2i_e_commerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.dao.*;

import java.util.Set;
import java.util.HashSet;

@Service
@Primary
public class BookServiceImpl implements BookService {

	@Autowired
    private BookRepository bookRepository;
	
	@Autowired
	private EditorRepository editorRepository;

	@Autowired
    private AuthorRepository authorRepository;

	@Override
	public Item save(Item item) throws Exception {
		return item;
	}

    @Override
    public Book save(Book book) {
        Editor editor = book.getEditor();
        if (editor != null) {
            Editor existingEditor = editorRepository.findByName(editor.getName());
            if (existingEditor != null) {
                book.setEditor(existingEditor);
            } else {
                editorRepository.save(editor);
            }
        }

        Set<Author> authors = book.getAuthors();
        if (authors != null) {
            Set<Author> updatedAuthors = new HashSet<>();
            for (Author author : authors) {
                List<Author> existingAuthors = authorRepository.findByFirstNameAndLastName(
                        author.getFirstName(), author.getLastName());
                if (!existingAuthors.isEmpty()) {
                    updatedAuthors.addAll(existingAuthors);
                } else {
                    authorRepository.save(author);
                    updatedAuthors.add(author);
                }
            }
            book.setAuthors(updatedAuthors);
        }

        return bookRepository.save(book);
    }

	@Override
	public List<Item> findAll() throws Exception {
		return null;
	}

	@Override
	public Optional<Item> findById(String id) throws Exception {
		return Optional.empty();
	}

	@Override
	public Item update(Item item) throws Exception {
		return null;
	}

	@Override
	public void deleteById(String id) throws Exception {		
	}

	@Override
	public Book update(Book book) {
		return null;
	}

	@Override
	public void deleteById(Long id) {		
	}

	@Override
	public void index(List<Item> items) throws Exception {
		
	} 
}


