package com.rest.library.domain;

import com.rest.library.repository.AuthorRepository;
import com.rest.library.repository.BookRepository;
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
public class BookTestSuite {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    BookRepository bookRepository;

    private static final String TEST_TITLE = "Test Title";
    private static final int TEST_PUBLICATION_YEAR = 2000;

    @Test
    public void testSaveNewBook() {
        //Given
        Author author = new Author();
        authorRepository.save(author);

        //When
        Book book = bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));
        Long bookId = book.getId();
        String bookTitle = book.getTitle();
        int bookPublicationYear = book.getPublicationYear();
        Author bookAuthor = book.getAuthor();
        List<Volume> bookVolumes = book.getVolumes();

        //Then
        assertEquals(TEST_TITLE, bookTitle);
        assertEquals(TEST_PUBLICATION_YEAR, bookPublicationYear);
        assertEquals(author, bookAuthor);
        assertNotNull(bookVolumes);

        //CleanUP
        bookRepository.deleteById(bookId);
        authorRepository.deleteById(author.getId());
    }

    @Test
    public void testBookAddVolume() {
        //Given
        Author author = new Author();
        authorRepository.save(author);
        Book book = bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));
        Volume volume = new Volume();
        assertTrue(book.getVolumes().isEmpty());
        assertNull(volume.getBook());

        //When
        book.addVolume(volume);

        //Then
        assertFalse(book.getVolumes().isEmpty());
        assertNotNull(volume.getBook());
        Book receivedBook = volume.getBook();
        Volume receivedVolume = book.getVolumes().get(0);
        assertEquals(volume, receivedVolume);
        assertEquals(book, receivedBook);

        //CleanUp
        bookRepository.deleteById(book.getId());
        authorRepository.deleteById(author.getId());
    }

    @Test
    public void testBookRemoveVolume() {
        //Given
        Author author = new Author();
        authorRepository.save(author);
        Book book = bookRepository.save(new Book(TEST_TITLE, TEST_PUBLICATION_YEAR, author));
        Volume volume = new Volume();
        assertTrue(book.getVolumes().isEmpty());
        assertNull(volume.getBook());
        book.addVolume(volume);
        assertFalse(book.getVolumes().isEmpty());
        assertNotNull(volume.getBook());

        //When
        book.removeVolume(volume);

        //Then
        assertTrue(book.getVolumes().isEmpty());
        assertNull(volume.getBook());

        //CleanUp
        bookRepository.deleteById(book.getId());
        authorRepository.deleteById(author.getId());
    }
}
