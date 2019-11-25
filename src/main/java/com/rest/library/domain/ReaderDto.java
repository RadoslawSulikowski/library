package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReaderDto {

    Long id;
    String firstName;
    String lastName;
    LocalDate creationDate;
    List<BorrowingDto> borrowingDtoList;
}
