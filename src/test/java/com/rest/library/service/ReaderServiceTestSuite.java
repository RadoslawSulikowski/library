package com.rest.library.service;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Reader;
import com.rest.library.domain.ReaderDto;
import com.rest.library.domain.ReaderSimpleDto;
import com.rest.library.exceptions.ReaderCantBeDeletedException;
import com.rest.library.exceptions.ReaderNotFoundException;
import com.rest.library.repository.ReaderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class ReaderServiceTestSuite {

    private static final String READER_FIRST_NAME = "Test First Name";
    private static final String READER_LAST_NAME = "Test Last Name";

    @Autowired
    ReaderService readerService;

    @Autowired
    ReaderRepository readerRepository;


    @Test
    public void testAddReader() {
        //Given

        //When
        Long readerId = readerService.addReader(new ReaderSimpleDto(READER_FIRST_NAME, READER_LAST_NAME));

        //Then
        assertTrue(readerRepository.findById(readerId).isPresent());

        //CleanUp
        readerRepository.deleteById(readerId);
    }

    @Test
    public void testGetReader() throws ReaderNotFoundException {
        //Given
        Long readerId = readerService.addReader(new ReaderSimpleDto(READER_FIRST_NAME, READER_LAST_NAME));
        assertTrue(readerRepository.findById(readerId).isPresent());
        Reader reader = readerRepository.findById(readerId).get();
        Borrowing b1 = new Borrowing();
        Borrowing b2 = new Borrowing();
        reader.addBorrowing(b1);
        reader.addBorrowing(b2);

        //When
        ReaderDto receivedReader = readerService.getReader(readerId);
        Long receivedReaderId = receivedReader.getId();
        String receivedReaderFirstName = receivedReader.getFirstName();
        String receivedReaderLastName = receivedReader.getLastName();

        //Then
        assertEquals(readerId, receivedReaderId);
        assertEquals(READER_FIRST_NAME, receivedReaderFirstName);
        assertEquals(READER_LAST_NAME, receivedReaderLastName);
        assertEquals(reader.getBorrowings().size(), receivedReader.getBorrowingIdList().size());

        //CleanUp
        readerRepository.deleteById(readerId);
    }

    @Test(expected = ReaderNotFoundException.class)
    public void testGetReaderThrowsReaderNotFoundException() throws ReaderNotFoundException {
        //Given

        //When
        readerService.getReader(-1L);

        //Then
        //throw ReaderNotFoundException

        //CleanUp
    }

    @Test
    public void testDeleteReader() throws ReaderNotFoundException, ReaderCantBeDeletedException {
        //Given
        Long readerId = readerService.addReader(new ReaderSimpleDto(READER_FIRST_NAME, READER_LAST_NAME));
        assertTrue(readerRepository.findById(readerId).isPresent());

        //When
        readerService.deleteReader(readerId);

        //Then
        assertFalse(readerRepository.findById(readerId).isPresent());

        //CleanUp

    }

    @Test(expected = ReaderNotFoundException.class)
    public void testDeleteReaderThrowsReaderNotFoundException() throws ReaderNotFoundException, ReaderCantBeDeletedException {
        //Given

        //When
        readerService.deleteReader(-1L);
        //Then
        //throw ReaderNotFoundException

        //CleanUp
    }

    @Test(expected = ReaderCantBeDeletedException.class)
    public void testDeleteReaderThrowsReaderCantBeDeletedException() throws ReaderNotFoundException, ReaderCantBeDeletedException {
        //Given
        Long readerId = readerService.addReader(new ReaderSimpleDto(READER_FIRST_NAME, READER_LAST_NAME));
        assertTrue(readerRepository.findById(readerId).isPresent());
        Reader reader = readerRepository.findById(readerId).get();
        Borrowing b1 = new Borrowing();
        reader.addBorrowing(b1);
        //When
        try {
            readerService.deleteReader(readerId);
            //Then
            //throw ReaderCantBeDeletedException

        } finally {
            readerRepository.deleteById(readerId);
            //CleanUp
        }
    }
}
