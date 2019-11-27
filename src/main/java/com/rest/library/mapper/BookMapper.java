package com.rest.library.mapper;

import com.rest.library.domain.Book;
import com.rest.library.domain.BookDto;
import com.rest.library.domain.Volume;
import com.rest.library.exceptions.AuthorNotFoundException;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.repository.AuthorRepository;
import com.rest.library.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookMapper.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private VolumeMapper volumeMapper;

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAuthor().getId(),
                book.getVolumes().stream().map(Volume::getId).collect(Collectors.toList())
        );
    }

    public Book mapToBook(BookDto bookDto) throws AuthorNotFoundException, VolumeNotFoundException {
        if (!authorRepository.findById(bookDto.getAuthorId()).isPresent()) {
            LOGGER.error("Can not map BookDto to Book - Author with id " + bookDto.getAuthorId() + " doesn't exist!");
            throw new AuthorNotFoundException("Author with id " + bookDto.getAuthorId() + " doesn't exist!");
        } else {
            return new Book(
                    bookDto.getId(),
                    bookDto.getTitle(),
                    bookDto.getPublicationYear(),
                    authorRepository.findById(bookDto.getAuthorId()).get(),
                    volumeMapper.mapToVolumeList(bookDto.getVolumeIdList())
            );
        }
    }

    public List<Book> mapToBookList(List<Long> bookIds) throws BookNotFoundException {
        List<Book> books = new ArrayList<>();
        for (Long bookId : bookIds) {
            if (bookRepository.findById(bookId).isPresent()) {
                books.add(bookRepository.findById(bookId).get());
            } else {
                LOGGER.error("Can not map BookIdList to BookList - Book with id " + bookId + " doesn't exist!");
                throw new BookNotFoundException("Book with id " + bookId + " doesn't exist!");
            }
        }
        return books;
    }
}
