package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BorrowingDto {

    Long id;
    Long volumeId;
    Long readerId;
    LocalDateTime borrowingDate;
    LocalDateTime returningDate;
}
