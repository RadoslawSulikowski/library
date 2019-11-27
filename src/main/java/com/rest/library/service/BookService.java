package com.rest.library.service;

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

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookService.class);
    private static final String MSG = "There is no book with id: ";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookMapper bookMapper;

    public void addBook(BookDto bookDto) throws AuthorNotFoundException, VolumeNotFoundException {
        Book book = bookRepository.save(bookMapper.mapToBook(bookDto));
        authorRepository.save(book.getAuthor().addBook(book));
        LOGGER.info("Book successful added with id " + book.getId());
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
