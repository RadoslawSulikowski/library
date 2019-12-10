package com.rest.library.domain;

import com.rest.library.repository.ReaderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ReaderTestSuite {

    private static final String READER_FIRST_NAME = "Test First Name";
    private static final String READER_LAST_NAME = "Test Last Name";

    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void testSaveNewReader() {
        //Given

        //When
        Reader reader = readerRepository.save(new Reader(READER_FIRST_NAME,READER_LAST_NAME));
        LocalDateTime creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Long readerId = reader.getId();
        String resultFirstName = reader.getFirstName();
        String resultLastName = reader.getLastName();
        LocalDateTime resultCreationDate = reader.getCreationDate();
        List<Borrowing> resultBorrowings = reader.getBorrowings();

        //Then
        assertTrue(readerRepository.findById(readerId).isPresent());
        assertEquals(READER_FIRST_NAME, resultFirstName);
        assertEquals(READER_LAST_NAME, resultLastName);
        assertEquals(creationDate, resultCreationDate);
        assertNotNull(resultBorrowings);

        //CleanUP
        readerRepository.deleteById(readerId);
    }

    @Test
    public void testReaderAddBorrowing() {
        //Given
        Reader reader = readerRepository.save(new Reader(READER_FIRST_NAME,READER_LAST_NAME));
        Long readerId = reader.getId();
        Borrowing borrowing = new Borrowing();
        assertTrue(reader.getBorrowings().isEmpty());
        assertNull(borrowing.getReader());

        //When
        reader.addBorrowing(borrowing);

        //Then
        assertFalse(reader.getBorrowings().isEmpty());
        assertNotNull(borrowing.getReader());

        //CleanUp
        readerRepository.deleteById(readerId);
    }

    @Test
    public void testReaderRemoveBorrowing() {
        //Given
        Reader reader = readerRepository.save(new Reader(READER_FIRST_NAME,READER_LAST_NAME));
        Long readerId = reader.getId();
        Borrowing borrowing = new Borrowing();
        assertTrue(reader.getBorrowings().isEmpty());
        assertNull(borrowing.getReader());
        reader.addBorrowing(borrowing);
        assertFalse(reader.getBorrowings().isEmpty());
        assertNotNull(borrowing.getReader());

        //When
        reader.removeBorrowing(borrowing);

        //Then
        assertTrue(reader.getBorrowings().isEmpty());
        assertNull(borrowing.getReader());

        //CleanUp
        readerRepository.deleteById(readerId);
    }
}
