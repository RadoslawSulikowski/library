package com.rest.library.mapper;

import com.rest.library.domain.Author;
import com.rest.library.domain.AuthorDto;
import com.rest.library.domain.Book;
import com.rest.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    @Autowired
    AuthorRepository authorRepository;

    public AuthorDto mapToAuthorDto(Author author) {
        return new AuthorDto(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getBooks().stream().map(Book::getId).collect(Collectors.toList())
        );
    }
}
