package com.rest.library.service;

import com.rest.library.domain.Author;
import com.rest.library.domain.Book;
import com.rest.library.domain.BookDto;
import com.rest.library.domain.Volume;
import com.rest.library.exceptions.AuthorNotFoundException;
import com.rest.library.exceptions.BookCantBeDeletedException;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.repository.AuthorRepository;
import com.rest.library.repository.BookRepository;
import com.rest.library.repository.VolumeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class BookServiceTestSuite {

    private static final String TEST_TITLE = "Test Title";
    private static final int TEST_PUBLICATION_YEAR = 2000;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    VolumeRepository volumeRepository;

    @Test
    public void testAddBook() throws AuthorNotFoundException {
        //Given
        Long authorId = authorRepository.save(new Author()).getId();

        //When
        Long bookId = bookService.addBook(TEST_TITLE, TEST_PUBLICATION_YEAR, authorId);

        //Then

        assertTrue(bookRepository.findById(bookId).isPresent());

        //CleanUp
        authorRepository.deleteById(authorId);
    }

    @Test(expected = AuthorNotFoundException.class)
    public void testAddBookThrowsAuthorNotFoundException() throws AuthorNotFoundException {
        //Given
        //When
        Long bookId = bookService.addBook(TEST_TITLE, TEST_PUBLICATION_YEAR, -1L);

        //Then
        //throw AuthorNotFoundException

        //CleanUp
    }

    @Test
    public void testGetBook() throws BookNotFoundException {
        //Given
        Author author = authorRepository.save(new Author());
        Long authorId = author.getId();
        Book book = bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));
        Long bookId = book.getId();

        //When
        BookDto receivedBook = bookService.getBook(bookId);
        Long receivedBookId = receivedBook.getId();
        String receivedBookTitle = receivedBook.getTitle();
        int receivedBookPublicationYear = receivedBook.getPublicationYear();
        Long receivedBookAuthorId = receivedBook.getAuthorId();

        //Then
        assertEquals(bookId, receivedBookId);
        assertEquals(TEST_TITLE, receivedBookTitle);
        assertEquals(TEST_PUBLICATION_YEAR, receivedBookPublicationYear);
        assertEquals(authorId, receivedBookAuthorId);

        //CleanUp
        authorRepository.deleteById(authorId);
    }

    @Test(expected = BookNotFoundException.class)
    public void testGetBookThrowsBookNotFoundException() throws BookNotFoundException {
        //Given
        Author author = authorRepository.save(new Author());
        bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));

        //When
        try {
            bookService.getBook(-1L);
            //Then
            //throw BookNotFoundException
        } finally {
            //CleanUp
            authorRepository.deleteById(author.getId());
        }
    }

    @Test
    public void testDeleteBook() throws BookNotFoundException, BookCantBeDeletedException {
        //Given
        Author author = authorRepository.save(new Author());
        Book book = bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));
        Long bookId = book.getId();
        assertTrue(bookRepository.findById(bookId).isPresent());

        //When
        bookService.deleteBook(bookId);

        //Then
        assertFalse(bookRepository.findById(bookId).isPresent());

        //CleanUp
        authorRepository.delete(author);
    }

    @Test(expected = BookNotFoundException.class)
    public void testDeleteBookThrowsBookNotFoundException() throws BookNotFoundException, BookCantBeDeletedException {
        //Given

        //When
        bookService.deleteBook(-1L);

        //Then
        //throw BookNotFoundException

        //CleanUp
    }

    @Test(expected = BookCantBeDeletedException.class)
    public void testDeleteBookThrowsBookCantBeDeletedException() throws BookNotFoundException, BookCantBeDeletedException {
        //Given
        Author author = authorRepository.save(new Author());
        Book book = bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));
        Volume volume = volumeRepository.save(new Volume());
        Long authorId = author.getId();
        Long bookId = book.getId();
        bookRepository.save(book.addVolume(volume));
        authorRepository.save(author.addBook(book));

        //When
        try {
            bookService.deleteBook(bookId);
            //Then
            //throw BookCantBeDeletedException
        } finally {
            //CleanUp
            authorRepository.deleteById(authorId);
        }
    }
}
