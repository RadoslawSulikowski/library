package com.rest.library.mapper;

import com.rest.library.domain.Author;
import com.rest.library.domain.AuthorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    @Autowired
    private BookMapper bookMapper;

    public AuthorDto mapToAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                bookMapper.mapToBookDtoList(author.getBooks())
        );
    }

    public Author mapToAuthor(AuthorDto authorDto) {
        return new Author(
                authorDto.getId(),
                authorDto.getFirstName(),
                authorDto.getLastName(),
                bookMapper.mapToBookList(authorDto.getBookDtoList())
        );
    }
}
