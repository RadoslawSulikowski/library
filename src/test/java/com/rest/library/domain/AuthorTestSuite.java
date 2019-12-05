package com.rest.library.domain;

import com.rest.library.repository.AuthorRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorTestSuite {

    private static final String TEST_FIRST_NAME = "Test First Name";
    private static final String TEST_LAST_NAME = "Test Last Name";

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveNewAuthor() {
        //Given

        //When
        Author author = authorRepository.save(new Author(TEST_FIRST_NAME, TEST_LAST_NAME));
        Long authorId = author.getId();
        String authorFirstName = author.getFirstName();
        String authorLastName = author.getLastName();
        List<Book> authorBooks = author.getBooks();

        //Then
        assertEquals(TEST_FIRST_NAME, authorFirstName);
        assertEquals(TEST_LAST_NAME, authorLastName);
        assertNotNull(authorBooks);

        //CleanUp
        authorRepository.deleteById(authorId);
    }

    @Test
    public void testAuthorAddBook() {
        //Given
        Author author = authorRepository.save(new Author(TEST_FIRST_NAME, TEST_LAST_NAME));
        Book book = new Book();
        assertTrue(author.getBooks().isEmpty());
        assertNull(book.getAuthor());

        //When
        author.addBook(book);

        //Then
        assertFalse(author.getBooks().isEmpty());
        assertNotNull(book.getAuthor());
        Author receivedAuthor = book.getAuthor();
        Book receivedBook = author.getBooks().get(0);
        assertEquals(author, receivedAuthor);
        assertEquals(book, receivedBook);

        //CleanUp
        authorRepository.deleteById(author.getId());
    }

    @Test
    public void testAuthorRemoveBook() {
        //Given
        Author author = authorRepository.save(new Author(TEST_FIRST_NAME, TEST_LAST_NAME));
        Book book = new Book();
        assertTrue(author.getBooks().isEmpty());
        assertNull(book.getAuthor());
        author.addBook(book);
        assertFalse(author.getBooks().isEmpty());
        assertNotNull(book.getAuthor());

        //When
        author.removeBook(book);

        //Then
        assertTrue(author.getBooks().isEmpty());
        assertNull(book.getAuthor());

        //CleanUp
        authorRepository.deleteById(author.getId());
    }

}
