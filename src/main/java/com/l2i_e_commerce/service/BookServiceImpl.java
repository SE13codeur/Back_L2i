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
	public void flush() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public <S extends Book> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Book> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void deleteAllInBatch(Iterable<Book> entities) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAllByIdInBatch(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Book getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Book getReferenceById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Book> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Book> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Book> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Book> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(Book entity) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAll(Iterable<? extends Book> entities) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<Book> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Page<Book> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Book> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}


	@Override
	public <S extends Book> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public <S extends Book> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public <S extends Book> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public <S extends Book, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Optional<Book> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
