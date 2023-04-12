package com.l2i_e_commerce.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.l2i_e_commerce.model.*;
import com.l2i_e_commerce.dao.*;

import com.meilisearch.sdk.*;

import com.l2i_e_commerce.configuration.MeiliSearchProperties;

@Service
@Primary
public class BookServiceImpl implements BookService {

	@Autowired
	private MeiliSearchProperties meiliSearchProperties;
	
	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private EditorRepository editorRepository;

	@Autowired
	private AuthorRepository authorRepository;
	
	@Override
	public void index(List<Item> items) throws Exception {
	    Config config = new Config(meiliSearchProperties.getUrl(), meiliSearchProperties.getApiKey());
	    Client client = new Client(config);
	    Index index = client.index("items");

	    for (Item item : items) {
	        if (item instanceof Book) {
	            Book book = (Book) item;
	            index.addDocuments(Collections.singletonList(book).toString());
	        }
	    }
	}





	@Override
	public Item save(Item item) throws Exception {
		if (item instanceof Book) {
			return save((Book) item);
		}
		throw new Exception("Invalid item type");
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Item> findAll() throws Exception {
		return (List<Item>)(List<?>) bookRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Optional<Item> findById(String id) throws Exception {
		Long bookId = Long.parseLong(id);
		return (Optional<Item>)(Optional<?>) bookRepository.findById(bookId);
	}

	@Override
	public Item update(Item item) throws Exception {
		if (item instanceof Book) {
			return update((Book) item);
		}
		throw new Exception("Invalid item type");
	}

	@Override
	public Book update(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public void deleteById(String id) throws Exception {
		Long bookId = Long.parseLong(id);
		bookRepository.deleteById(bookId);
	}




	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

}
