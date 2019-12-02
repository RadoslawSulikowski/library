package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.BorrowingDto;
import org.springframework.stereotype.Component;

@Component
public class BorrowingMapper {

    public BorrowingDto mapToBorrowingDto(Borrowing borrowing) {
        return new BorrowingDto(
                borrowing.getId(),
                borrowing.getVolume().getId(),
                borrowing.getReader().getId(),
                borrowing.getBorrowingDate(),
                borrowing.getReturningDate()
        );
    }
}
