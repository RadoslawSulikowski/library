package com.rest.library.domain;

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
public class VolumeTestSuite {

    private static final String TEST_VOLUME_STATUS = "Test volume status";

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testSaveNewVolume() {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();

        //When
        Volume volume = volumeRepository.save(new Volume(book, TEST_VOLUME_STATUS));
        Long volumeId = volume.getId();
        Book volumeBook = volume.getBook();
        String volumeStatus = volume.getStatus();

        //Then
        assertNotNull(volumeId);
        assertEquals(TEST_VOLUME_STATUS, volumeStatus);
        assertEquals(book, volumeBook);

        //CleanUp
        bookRepository.deleteById(bookId);
    }

    @Test
    public void testVolumeAddBorrowing() {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        Volume volume = volumeRepository.save(new Volume(book, TEST_VOLUME_STATUS));
        Long volumeId = volume.getId();
        Borrowing borrowing = new Borrowing();
        assertTrue(volume.getBorrowings().isEmpty());
        assertNull(borrowing.getVolume());

        //When
        volume.addBorrowing(borrowing);

        //Then
        assertFalse(volume.getBorrowings().isEmpty());
        assertNotNull(borrowing.getVolume());

        //CleanUp
        bookRepository.deleteById(bookId);
    }

    @Test
    public void testVolumeRemoveBorrowing() {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        Volume volume = volumeRepository.save(new Volume(book, TEST_VOLUME_STATUS));
        Long volumeId = volume.getId();
        Borrowing borrowing = new Borrowing();
        assertTrue(volume.getBorrowings().isEmpty());
        assertNull(borrowing.getVolume());
        volume.addBorrowing(borrowing);
        assertFalse(volume.getBorrowings().isEmpty());
        assertNotNull(borrowing.getVolume());

        //When
        volume.removeBorrowing(borrowing);

        //Then
        assertTrue(volume.getBorrowings().isEmpty());
        assertNull(borrowing.getVolume());

        //CleanUp
        bookRepository.deleteById(bookId);
    }
}
