package com.rest.library.mapper;

import com.rest.library.domain.Author;
import com.rest.library.domain.AuthorDto;
import com.rest.library.domain.Book;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper {

    @Autowired
    private BookMapper bookMapper;

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

    public Author mapToAuthor(AuthorDto authorDto) throws BookNotFoundException {
        if (authorDto.getId() != null && authorRepository.findById(authorDto.getId()).isPresent()) {
            return new Author(
                    authorDto.getId(),
                    authorDto.getFirstName(),
                    authorDto.getLastName(),
                    bookMapper.mapToBookList(authorDto.getBookIdList())
            );
        } else {
            return new Author(
                    authorDto.getFirstName(),
                    authorDto.getLastName(),
                    bookMapper.mapToBookList(authorDto.getBookIdList())
            );
        }
    }
}
