package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.BorrowingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BorrowingMapper {

    @Autowired
    private VolumeMapper volumeMapper;

    @Autowired
    private ReaderMapper readerMapper;

    public BorrowingDto mapToBorrowingDto(Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                volumeMapper.mapToVolumeDto(borrowing.getVolume()),
                readerMapper.mapToReaderDto(borrowing.getReader()),
                borrowing.getBorrowingDate(),
                borrowing.getReturningDate()
        );
    }

    public Borrowing mapToBorrowing(BorrowingDto borrowingDto) {
        return new Borrowing(
                borrowingDto.getId(),
                volumeMapper.mapToVolume(borrowingDto.getVolumeDto()),
                readerMapper.mapToReader(borrowingDto.getReaderDto()),
                borrowingDto.getBorrowingDate(),
                borrowingDto.getReturningDate()
        );
    }

    public List<BorrowingDto> mapToBorrowingDtoList(List<Borrowing> borrowings) {
        List<BorrowingDto> borrowingDtos = new ArrayList<>();
        borrowings.forEach(b -> borrowingDtos.add(mapToBorrowingDto(b)));
        return borrowingDtos;
    }

    public List<Borrowing> mapToBorrowingList(List<BorrowingDto> borrowingDtos) {
        List<Borrowing> borrowings = new ArrayList<>();
        borrowingDtos.forEach(b -> borrowings.add(mapToBorrowing(b)));
        return borrowings;
    }
}
