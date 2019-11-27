package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.BorrowingDto;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.ReaderNotFoundException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.repository.BorrowingRepository;
import com.rest.library.repository.ReaderRepository;
import com.rest.library.repository.VolumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowingMapper.class);
    @Autowired
    BorrowingRepository borrowingRepository;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private ReaderRepository readerRepository;

    public BorrowingDto mapToBorrowingDto(Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getVolume().getId(),
                borrowing.getReader().getId(),
                borrowing.getBorrowingDate(),
                borrowing.getReturningDate()
        );
    }

    public Borrowing mapToBorrowing(BorrowingDto borrowingDto) throws VolumeNotFoundException, ReaderNotFoundException {
        if (!volumeRepository.findById(borrowingDto.getVolumeId()).isPresent()) {
            LOGGER.error("Can not map BorrowingDto to Borrowing" +
                    " - Volume with id " + borrowingDto.getVolumeId() + " doesn't exist!");
            throw new VolumeNotFoundException("Volume with id " + borrowingDto.getVolumeId() + " doesn't exist!");
        } else if (!readerRepository.findById(borrowingDto.getReaderId()).isPresent()) {
            LOGGER.error("Can not map BorrowingDto to Borrowing" +
                    " - Reader with id " + borrowingDto.getReaderId() + " doesn't exist!");
            throw new ReaderNotFoundException("Reader with id " + borrowingDto.getReaderId() + " doesn't exist!");
        } else {
            return new Borrowing(
                    borrowingDto.getId(),
                    volumeRepository.findById(borrowingDto.getVolumeId()).get(),
                    readerRepository.findById(borrowingDto.getReaderId()).get(),
                    borrowingDto.getBorrowingDate(),
                    borrowingDto.getReturningDate()
            );
        }
    }

    public List<Borrowing> mapToBorrowingList(List<Long> borrowingIds) throws BorrowingNotFoundException {
        List<Borrowing> borrowings = new ArrayList<>();
        for (Long borrowingId : borrowingIds) {
            if (borrowingRepository.findById(borrowingId).isPresent()) {
                borrowings.add(borrowingRepository.findById(borrowingId).get());
            } else {
                LOGGER.error("Can not map BorrowingIdList to BorrowingList" +
                        " - Borrowing with id " + borrowingId + " doesn't exist!");
                throw new BorrowingNotFoundException("Borrowing with id " + borrowingId + " doesn't exist!");
            }
        }
        return borrowings;
    }
}
