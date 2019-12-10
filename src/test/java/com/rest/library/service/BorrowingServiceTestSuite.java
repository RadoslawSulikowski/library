package com.rest.library.service;

import com.rest.library.domain.Book;
import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Reader;
import com.rest.library.domain.Volume;
import com.rest.library.exceptions.*;
import com.rest.library.repository.BorrowingRepository;
import com.rest.library.repository.ReaderRepository;
import com.rest.library.repository.VolumeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BorrowingServiceTestSuite {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private ReaderRepository readerRepository;

    @Test
    public void testBorrowVolume()
            throws VolumeNotFoundException, ReaderNotFoundException, VolumeCantBeBorrowedException {
        //Given
        Volume volume = volumeRepository.save(new Volume(new Book(), "toBorrow"));
        Long volumeId = volume.getId();
        Reader reader = readerRepository.save(new Reader());
        Long readerId = reader.getId();

        //When
        Long borrowingId = borrowingService.borrowVolume(volumeId, readerId);

        //Then
        assertTrue(borrowingRepository.findById(borrowingId).isPresent());
        assertEquals("borrowed", volume.getStatus());
        assertEquals(borrowingId, volume.getBorrowings().get(0).getId());
        assertEquals(borrowingId, reader.getBorrowings().get(0).getId());

        //CleanUp
        borrowingRepository.deleteById(borrowingId);
        volumeRepository.deleteById(volumeId);
        readerRepository.deleteById(readerId);
    }

    @Test(expected = VolumeNotFoundException.class)
    public void testBorrowVolumeThrowsVolumeNotFoundException()
            throws VolumeNotFoundException, ReaderNotFoundException, VolumeCantBeBorrowedException {
        //Given
        Reader reader = readerRepository.save(new Reader());
        Long readerId = reader.getId();

        //When
        try {
            borrowingService.borrowVolume(-1L, readerId);

            //Then
            //throw VolumeNotFoundException
        } finally {
            //CleanUp
            readerRepository.deleteById(readerId);
        }
    }

    @Test(expected = ReaderNotFoundException.class)
    public void testBorrowVolumeThrowsReaderNotFoundException()
            throws VolumeNotFoundException, ReaderNotFoundException, VolumeCantBeBorrowedException {
        //Given
        Volume volume = volumeRepository.save(new Volume());
        Long volumeId = volume.getId();

        //When
        try {
            borrowingService.borrowVolume(volumeId, -1L);

            //Then
            //throw ReaderNotFoundException
        } finally {
            //CleanUp
            volumeRepository.deleteById(volumeId);
        }
    }

    @Test(expected = VolumeCantBeBorrowedException.class)
    public void testBorrowVolumeThrowsVolumeCantBeBorrowedException()
            throws VolumeNotFoundException, ReaderNotFoundException, VolumeCantBeBorrowedException {
        //Given
        Volume volume = (new Volume());
        volume.setStatus("Test Status different than 'toBorrow'");
        volumeRepository.save(volume);
        Long volumeId = volume.getId();
        Reader reader = readerRepository.save(new Reader());
        Long readerId = reader.getId();

        //When
        try {
            borrowingService.borrowVolume(volumeId, readerId);

            //Then
            //throw VolumeCantBeBorrowedException
        } finally {
            //CleanUp
            volumeRepository.deleteById(volumeId);
            readerRepository.deleteById(readerId);
        }
    }

    @Test
    public void testReturnVolume()
            throws VolumeNotFoundException, ReaderNotFoundException, BorrowingNotFoundException,
            VolumeAlreadyReturnedException, VolumeCantBeBorrowedException {
        //Given
        Volume volume = volumeRepository.save(new Volume(new Book(), "toBorrow"));
        Long volumeId = volume.getId();
        Reader reader = readerRepository.save(new Reader());
        Long readerId = reader.getId();
        Long borrowingId = borrowingService.borrowVolume(volumeId, readerId);
        assertTrue(borrowingRepository.findById(borrowingId).isPresent());
        Borrowing borrowing = borrowingRepository.findById(borrowingId).get();

        //When
        borrowingService.returnVolume(volumeId);
        LocalDateTime returningDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        //Then
        assertEquals("returned", volume.getStatus());
        assertEquals(returningDate, borrowing.getReturningDate());

        //CleanUp
        borrowingRepository.deleteById(borrowingId);
        volumeRepository.deleteById(volumeId);
        readerRepository.deleteById(readerId);
    }

    @Test(expected = VolumeNotFoundException.class)
    public void testReturnVolumeThrowsVolumeNotFoundException()
            throws VolumeNotFoundException, BorrowingNotFoundException, VolumeAlreadyReturnedException {
        //Given

        //When
        borrowingService.returnVolume(-1L);
    }

    @Test(expected = BorrowingNotFoundException.class)
    public void testReturnVolumeThrowsBorrowingNotFoundException()
            throws VolumeNotFoundException, BorrowingNotFoundException, VolumeAlreadyReturnedException {
        //Given
        Volume volume = volumeRepository.save(new Volume());
        Long volumeId = volume.getId();

        //When
        try {
            borrowingService.returnVolume(volumeId);
            //Then
            //throw BorrowingNotFoundException
        } finally {
            //CleanUp
            volumeRepository.deleteById(volumeId);
        }
    }

    @Test(expected = VolumeAlreadyReturnedException.class)
    public void testReturnVolumeThrowsVolumeAlreadyReturnedException()
            throws VolumeNotFoundException, ReaderNotFoundException, BorrowingNotFoundException,
            VolumeAlreadyReturnedException, VolumeCantBeBorrowedException {
        //Given
        Volume volume = volumeRepository.save(new Volume(new Book(), "toBorrow"));
        Long volumeId = volume.getId();
        Reader reader = readerRepository.save(new Reader());
        Long readerId = reader.getId();
        Long borrowingId = borrowingService.borrowVolume(volumeId, readerId);
        assertTrue(borrowingRepository.findById(borrowingId).isPresent());
        Borrowing borrowing = borrowingRepository.findById(borrowingId).get();
        borrowing.setReturningDate(LocalDateTime.now());

        //When
        try {
            borrowingService.returnVolume(volumeId);
            //Then
            //throw VolumeAlreadyReturnedException
        } finally {
            //CleanUp
            borrowingRepository.deleteById(borrowingId);
            volumeRepository.deleteById(volumeId);
            readerRepository.deleteById(readerId);
        }
    }
}
