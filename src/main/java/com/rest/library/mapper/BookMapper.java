package com.rest.library.mapper;

import com.rest.library.domain.Book;
import com.rest.library.domain.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private VolumeMapper volumeMapper;

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getPublicationYear(),
                authorMapper.mapToAuthorDto(book.getAuthor()),
                volumeMapper.mapToVolumeDtoList(book.getVolumes())

        );
    }

    public Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getId(),
                bookDto.getTitle(),
                bookDto.getPublicationYear(),
                authorMapper.mapToAuthor(bookDto.getAuthorDto()),
                volumeMapper.mapToVolumeList(bookDto.getVolumeDtoList())
        );
    }

    public List<BookDto> mapToBookDtoList(List<Book> books) {
        List<BookDto> bookDtos = new ArrayList<>();
        books.forEach(b -> bookDtos.add(mapToBookDto(b)));
        return bookDtos;
    }

    public List<Book> mapToBookList(List<BookDto> bookDtos) {
        List<Book> books = new ArrayList<>();
        bookDtos.forEach(b -> books.add(mapToBook(b)));
        return books;
    }
}
