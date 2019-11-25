package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VolumeDto {
    Long id;
    BookDto bookDto;
    String status;
    List<BorrowingDto> borrowingDtoList;
}
