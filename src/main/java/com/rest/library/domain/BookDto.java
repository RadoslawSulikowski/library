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
public class BookDto {
    Long id;
    String title;
    LocalDate publicationYear;
    AuthorDto authorDto;
    List<VolumeDto> volumeDtoList;

}
