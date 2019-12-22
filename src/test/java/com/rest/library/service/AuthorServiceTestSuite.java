package com.rest.library.service;

import com.rest.library.domain.Author;
import com.rest.library.domain.AuthorDto;
import com.rest.library.domain.AuthorSimpleDto;
import com.rest.library.domain.Book;
import com.rest.library.exceptions.AuthorCantBeDeletedException;
import com.rest.library.exceptions.AuthorNotFoundException;
import com.rest.library.repository.AuthorRepository;
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
public class AuthorServiceTestSuite {

    private static final String TEST_FIRST_NAME = "Test First Name";
    private static final String TEST_LAST_NAME = "Test Last Name";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Test
    public void testAddAuthor() {
        //Given
        AuthorSimpleDto authorSimpleDto = new AuthorSimpleDto(TEST_FIRST_NAME, TEST_LAST_NAME);
        //When
        Long authorId = authorService.addAuthor(authorSimpleDto);

        //Then
        assertTrue(authorRepository.findById(authorId).isPresent());

        //CleanUp
        authorRepository.deleteById(authorId);
    }

    @Test
    public void testGetAuthor() throws AuthorNotFoundException {
        //Given
        AuthorSimpleDto authorSimpleDto = new AuthorSimpleDto(TEST_FIRST_NAME, TEST_LAST_NAME);
        Long authorId = authorService.addAuthor(authorSimpleDto);
        assertTrue(authorRepository.findById(authorId).isPresent());
        Author author = authorRepository.findById(authorId).get();
        Book book1 = new Book();
        Book book2 = new Book();
        author.addBook(book1);
        author.addBook(book2);

        //When
        AuthorDto receivedAuthor = authorService.getAuthor(authorId);
        Long receivedAuthorId = receivedAuthor.getId();
        String receivedAuthorFirstName = receivedAuthor.getFirstName();
        String receivedAuthorLastName = receivedAuthor.getLastName();

        //Then
        assertEquals(authorId, receivedAuthorId);
        assertEquals(TEST_FIRST_NAME, receivedAuthorFirstName);
        assertEquals(TEST_LAST_NAME, receivedAuthorLastName);
        assertEquals(author.getBooks().size(), receivedAuthor.getBookIdList().size());

        //CleanUp
        authorRepository.deleteById(authorId);
    }

    @Test(expected = AuthorNotFoundException.class)
    public void testGetAuthorThrowsAuthorNotFoundException() throws AuthorNotFoundException {
        //Given

        //When
        authorService.getAuthor(-1L);

        //Then
        //throw AuthorNotFoundException

        //CleanUp
    }

    @Test
    public void testDeleteAuthor() throws AuthorNotFoundException, AuthorCantBeDeletedException {
        //Given
        AuthorSimpleDto authorSimpleDto = new AuthorSimpleDto(TEST_FIRST_NAME, TEST_LAST_NAME);
        Long authorId = authorService.addAuthor(authorSimpleDto);
        assertTrue(authorRepository.findById(authorId).isPresent());
        //When
        authorService.deleteAuthor(authorId);
        //Then
        assertFalse(authorRepository.findById(authorId).isPresent());
        //CleanUp
    }

    @Test(expected = AuthorNotFoundException.class)
    public void testDeleteAuthorThrowsAuthorNotFoundException() throws AuthorNotFoundException, AuthorCantBeDeletedException {
        //Given

        //When
        authorService.deleteAuthor(-1L);

        //Then
        //throw AuthorNotFoundException

        //CleanUp
    }

    @Test(expected = AuthorCantBeDeletedException.class)
    public void testDeleteAuthorThrowsAuthorCantBeDeletedException() throws AuthorNotFoundException, AuthorCantBeDeletedException {
        //Given
        AuthorSimpleDto authorSimpleDto = new AuthorSimpleDto(TEST_FIRST_NAME, TEST_LAST_NAME);
        Long authorId = authorService.addAuthor(authorSimpleDto);
        assertTrue(authorRepository.findById(authorId).isPresent());
        Author author = authorRepository.findById(authorId).get();
        Book book = new Book("TestBookTitle", 2000, author);
        author.addBook(book);

        //When
        try {
            authorService.deleteAuthor(authorId);
            //Then
            //throw AuthorCantBeDeletedException
        } finally {
            //CleanUp
            author.removeBook(book);
            authorService.deleteAuthor(authorId);
        }
    }
}
