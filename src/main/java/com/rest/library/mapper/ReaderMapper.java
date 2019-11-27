package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Reader;
import com.rest.library.domain.ReaderDto;
import com.rest.library.exceptions.BorrowingNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReaderMapper {

    @Autowired
    private BorrowingMapper borrowingMapper;

    public ReaderDto mapToReaderDto(Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getFirstName(),
                reader.getLastName(),
                reader.getCreationDate(),
                reader.getBorrowings().stream().map(Borrowing::getId).collect(Collectors.toList())
        );
    }

    public Reader mapToReader(ReaderDto readerDto) throws BorrowingNotFoundException {
        return new Reader(
                readerDto.getId(),
                readerDto.getFirstName(),
                readerDto.getLastName(),
                readerDto.getCreationDate(),
                borrowingMapper.mapToBorrowingList(readerDto.getBorrowingIdList())
        );
    }


}
