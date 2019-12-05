package com.rest.library.service;

import com.rest.library.domain.Book;
import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Volume;
import com.rest.library.domain.VolumeDto;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.VolumeCantBeDeletedException;
import com.rest.library.exceptions.VolumeNotFoundException;
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
public class VolumeServiceTestSuite {

    private static final String TEST_VOLUME_STATUS = "Test volume status";

    @Autowired
    private VolumeService volumeService;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testAddVolume() throws BookNotFoundException {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();

        //When
        Long volumeId = volumeService.addVolume(bookId, TEST_VOLUME_STATUS);

        //Then
        assertTrue(volumeRepository.findById(volumeId).isPresent());

        //CleanUp
        bookRepository.deleteById(bookId);
    }

    @Test(expected = BookNotFoundException.class)
    public void testAddVolumeThrowsBookNotFoundException() throws BookNotFoundException {
        //Given

        //When
        volumeService.addVolume(-1L, TEST_VOLUME_STATUS);

        //Then
        //throw BookNotFoundException

        //CleanUp
    }

    @Test
    public void testGetNumberOfAvailableVolumesOfBook() throws BookNotFoundException {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        assertTrue(bookRepository.findById(bookId).isPresent());
        Long volume1Id = volumeService.addVolume(bookId, "toBorrow");
        assertTrue(volumeRepository.findById(volume1Id).isPresent());
        Long volume2Id = volumeService.addVolume(bookId, "toBorrow");
        assertTrue(volumeRepository.findById(volume2Id).isPresent());
        Long volume3Id = volumeService.addVolume(bookId, "borrowed");
        assertTrue(volumeRepository.findById(volume3Id).isPresent());
        assertEquals(3, bookRepository.findById(bookId).get().getVolumes().size());

        //When
        int numberOfAvailableVolumes = volumeService.getNumberOfAvailableVolumesOfBook(bookId);

        //Then
        assertEquals(2, numberOfAvailableVolumes);

        //CleanUp
        bookRepository.deleteById(bookId);
        assertFalse(bookRepository.findById(bookId).isPresent());
    }

    @Test(expected = BookNotFoundException.class)
    public void testGetNumberOfAvailableVolumesOfBookThrowsBookNotFoundException() throws BookNotFoundException {
        //Given

        // When
        volumeService.getNumberOfAvailableVolumesOfBook(-1L);

        //Then
        //throw BookNotFoundException

        //CleanUp
    }

    @Test
    public void testChangeVolumeStatus() throws VolumeNotFoundException {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        Volume volume = volumeRepository.save(new Volume(book, TEST_VOLUME_STATUS));
        Long volumeId = volume.getId();
        assertTrue(volumeRepository.findById(volumeId).isPresent());

        //When
        volumeService.changeVolumeStatus(volumeId, "Changed volume status");

        //Then
        assertEquals("Changed volume status", volumeRepository.findById(volumeId).get().getStatus());

        //CleanUp
        bookRepository.deleteById(bookId);
    }

    @Test(expected = VolumeNotFoundException.class)
    public void testChangeVolumeStatusThrowsVolumeNotFoundException() throws VolumeNotFoundException {
        //Given

        //When
        volumeService.changeVolumeStatus(-1L, "");

        //Then
        //throw VolumeNotFoundException

        //CleanUp
    }

    @Test
    public void testGetVolume() throws VolumeNotFoundException, BookNotFoundException {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        Long volumeId = volumeService.addVolume(bookId, TEST_VOLUME_STATUS);
        assertTrue(volumeRepository.findById(volumeId).isPresent());
        int volumeBorrowingsSize = volumeRepository.findById(volumeId).get().getBorrowings().size();

        //When
        VolumeDto resultVolume = volumeService.getVolume(volumeId);
        Long resultVolumeId = resultVolume.getId();
        Long resultVolumeBookId = resultVolume.getBookId();
        String resultVolumeStatus = resultVolume.getStatus();
        int resultVolumeBorrowingsSize = resultVolume.getBorrowingIdList().size();

        //Then
        assertEquals(volumeId, resultVolumeId);
        assertEquals(bookId, resultVolumeBookId);
        assertEquals(TEST_VOLUME_STATUS, resultVolumeStatus);
        assertEquals(volumeBorrowingsSize, resultVolumeBorrowingsSize);

        //CleanUp
        bookRepository.deleteById(bookId);
    }

    @Test(expected = VolumeNotFoundException.class)
    public void testGetVolumeThrowsVolumeNotFoundException() throws VolumeNotFoundException {
        //Given

        //When
        volumeService.getVolume(-1L);

        //Then
        //throw VolumeNotFoundException

        //CleanUp
    }

    @Test
    public void testDeleteVolume() throws VolumeNotFoundException, VolumeCantBeDeletedException, BookNotFoundException {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        assertTrue(bookRepository.findById(bookId).isPresent());
        Long volumeId = volumeService.addVolume(bookId, TEST_VOLUME_STATUS);
        assertTrue(volumeRepository.findById(volumeId).isPresent());
        assertFalse(bookRepository.findById(bookId).get().getVolumes().isEmpty());

        //When
        volumeService.deleteVolume(volumeId);

        //Then
        assertTrue(bookRepository.findById(bookId).get().getVolumes().isEmpty());
        assertFalse(volumeRepository.findById(volumeId).isPresent());

        //CleanUp
        bookRepository.deleteById(bookId);
    }

    @Test(expected = VolumeNotFoundException.class)
    public void testDeleteVolumeThrowsVolumeNotFoundException() throws VolumeNotFoundException, VolumeCantBeDeletedException {
        //Given

        //When
        volumeService.deleteVolume(-1L);

        //Then
        //throw VolumeNotFoundException

        //CleanUp
    }

    @Test(expected = VolumeCantBeDeletedException.class)
    public void testDeleteVolumeThrowsVolumeCantBeDeletedException() throws VolumeNotFoundException, VolumeCantBeDeletedException, BookNotFoundException {
        //Given
        Book book = bookRepository.save(new Book());
        Long bookId = book.getId();
        Long volumeId = volumeService.addVolume(bookId, TEST_VOLUME_STATUS);
        assertTrue(volumeRepository.findById(volumeId).isPresent());
        Volume volume = volumeRepository.findById(volumeId).get();
        Borrowing borrowing = new Borrowing();
        volumeRepository.save(volume.addBorrowing(borrowing));

        //When
        try {
            volumeService.deleteVolume(volumeId);

            //Then
            //throw VolumeCantBeDeletedException
        } finally {
            //CleanUp
            bookRepository.deleteById(bookId);
        }
    }
}
