package com.rest.library.mapper;

import com.rest.library.domain.Book;
import com.rest.library.domain.BookDto;
import com.rest.library.domain.Volume;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                book.getAuthor().getId(),
                book.getVolumes().stream().map(Volume::getId).collect(Collectors.toList())
        );
    }
}
