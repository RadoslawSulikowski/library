package com.rest.library.service;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.BorrowingDto;
import com.rest.library.domain.Reader;
import com.rest.library.domain.Volume;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.ReaderNotFoundException;
import com.rest.library.exceptions.VolumeAlreadyReturnedException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.mapper.BorrowingMapper;
import com.rest.library.repository.BorrowingRepository;
import com.rest.library.repository.ReaderRepository;
import com.rest.library.repository.VolumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BorrowingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowingService.class);
    private static final String MSG = "There is no Borrowing with id ";

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BorrowingMapper borrowingMapper;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private ReaderRepository readerRepository;

    public Long borrowVolume(Long volumeId, Long readerId) throws VolumeNotFoundException, ReaderNotFoundException {
        if (!volumeRepository.findById(volumeId).isPresent()) {
            LOGGER.error("Can not add Borrowing" +
                    " - Volume with id " + volumeId + " doesn't exist!");
            throw new VolumeNotFoundException("Volume with id " + volumeId + " doesn't exist!");
        } else if (!readerRepository.findById(readerId).isPresent()) {
            LOGGER.error("Can not add Borrowing" +
                    " - Reader with id " + readerId + " doesn't exist!");
            throw new ReaderNotFoundException("Reader with id " + readerId + " doesn't exist!");
        } else {
            Volume volume = volumeRepository.findById(volumeId).get();
            volume.setStatus("borrowed");
            Reader reader = readerRepository.findById(readerId).get();
            Borrowing borrowing = borrowingRepository.save(new Borrowing(volume, reader));
            volumeRepository.save(volume.addBorrowing(borrowing));
            readerRepository.save(reader.addBorrowing(borrowing));
            LOGGER.info("Volume with id " + volumeId + " successful borrowed by reader with id " + readerId);
            return borrowing.getId();
        }
    }

    public void returnVolume(Long volumeId) throws VolumeNotFoundException, BorrowingNotFoundException, VolumeAlreadyReturnedException {
        if (!volumeRepository.findById(volumeId).isPresent()) {
            LOGGER.error("Can not return Volume" +
                    " - Volume with id " + volumeId + " doesn't exist!");
            throw new VolumeNotFoundException("Volume with id " + volumeId + " doesn't exist!");
        } else {
            Volume volume = volumeRepository.findById(volumeId).get();
            if (volume.getBorrowings() == null || volume.getBorrowings().isEmpty()) {
                LOGGER.error("Given Volume has never been borrowed!");
                throw new BorrowingNotFoundException("Given Volume has never been borrowed!");
            } else {
                Borrowing borrowing = volume.getBorrowings().get(volume.getBorrowings().size() - 1);
                if (borrowing.getReturningDate() != null) {
                    LOGGER.warn("Volume already returned");
                    throw new VolumeAlreadyReturnedException("Volume already returned");
                } else {
                    volume.setStatus("returned");
                    borrowing.setReturningDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
                    volumeRepository.save(volume);
                    borrowingRepository.save(borrowing);
                }
            }
        }
    }


    public List<BorrowingDto> getAllBorrowings() {
        List<BorrowingDto> borrowingDtos = new ArrayList<>();
        borrowingRepository.findAll().forEach(b -> borrowingDtos.add(borrowingMapper.mapToBorrowingDto(b)));
        return borrowingDtos;
    }

    public BorrowingDto getBorrowing(Long id) throws BorrowingNotFoundException {
        if (borrowingRepository.findById(id).isPresent()) {
            return borrowingMapper.mapToBorrowingDto(borrowingRepository.findById(id).get());
        } else {
            LOGGER.error(MSG + id);
            throw new BorrowingNotFoundException(MSG + id);
        }
    }
}
