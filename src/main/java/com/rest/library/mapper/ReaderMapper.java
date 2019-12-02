package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Reader;
import com.rest.library.domain.ReaderDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReaderMapper {

    public ReaderDto mapToReaderDto(Reader reader) {
        return new ReaderDto(
                reader.getId(),
                reader.getFirstName(),
                reader.getLastName(),
                reader.getCreationDate(),
                reader.getBorrowings().stream().map(Borrowing::getId).collect(Collectors.toList())
        );
    }
}
