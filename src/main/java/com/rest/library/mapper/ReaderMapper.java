package com.rest.library.mapper;

import com.rest.library.domain.Reader;
import com.rest.library.domain.ReaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                borrowingMapper.mapToBorrowingDtoList(reader.getBorrowings())
        );
    }

    public Reader mapToReader(ReaderDto readerDto) {
        return new Reader(
                readerDto.getId(),
                readerDto.getFirstName(),
                readerDto.getLastName(),
                readerDto.getCreationDate(),
                borrowingMapper.mapToBorrowingList(readerDto.getBorrowingDtoList())
        );
    }


}
