package com.rest.library.service;

import com.rest.library.domain.Author;
import com.rest.library.domain.Book;
import com.rest.library.domain.BookDto;
import com.rest.library.exceptions.*;
import com.rest.library.mapper.BookMapper;
import com.rest.library.repository.AuthorRepository;
import com.rest.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BookService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private static final String MSG = "There is no book with id: ";

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public void addBook(String title, int publicationYear, Long authorId) throws AuthorNotFoundException {
        if (authorRepository.findById(authorId).isPresent()) {
            Author author = authorRepository.findById(authorId).get();
            Book book = bookRepository.save(new Book(title, publicationYear, author));
            authorRepository.save(author.addBook(book));
            LOGGER.info("Book successful added with id " + book.getId());
        } else {
            LOGGER.error("Can't add Book - there is no author with id " + authorId);
            throw new AuthorNotFoundException("Can't add Book - there is no author with id " + authorId);
        }
    }

    public BookDto getBook(Long id) throws BookNotFoundException {
        if (bookRepository.findById(id).isPresent()) {
            return bookMapper.mapToBookDto(bookRepository.findById(id).get());
        } else {
            throw new BookNotFoundException(MSG + id);
        }
    }

    public List<BookDto> getAllBooks() {
        List<BookDto> bookDtos = new ArrayList<>();
        bookRepository.findAll().forEach(b -> bookDtos.add(bookMapper.mapToBookDto(b)));
        return bookDtos;
    }

    public void deleteBook(Long id) throws BookNotFoundException, BookCantBeDeletedException {
        if (bookRepository.findById(id).isPresent()) {
            if (bookRepository.findById(id).get().getVolumes().isEmpty()) {
                bookRepository.deleteById(id);
                LOGGER.info("Book with id " + id + " was successful deleted.");
            } else {
                LOGGER.error("There are volumes of this book in DB.");
                throw new BookCantBeDeletedException("There are volumes of this book in DB");
            }
        } else {
            LOGGER.error(MSG + id);
            throw new BookNotFoundException(MSG + id);
        }
    }
}
