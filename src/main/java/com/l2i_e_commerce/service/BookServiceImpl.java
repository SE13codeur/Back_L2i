package com.l2i_e_commerce.service;

import java.util.*;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.dao.*;

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


	public Book update(Book book) {
		return bookRepository.save(book);
	}

	@Override
    public List<Book> findAll() {

        return bookRepository.findAll();
    }

	@Override
	public Optional<Book> findById(Long aLong) throws Exception {
		return Optional.empty();
	}

}
