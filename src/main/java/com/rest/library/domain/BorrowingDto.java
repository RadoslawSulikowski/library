package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BorrowingDto {
    Long id;
    Long volumeId;
    Long readerId;
    LocalDate borrowingDate;
    LocalDate returningDate;

}
