package com.rest.library.service;

import com.rest.library.domain.Author;
import com.rest.library.domain.AuthorDto;
import com.rest.library.domain.AuthorSimpleDto;
import com.rest.library.exceptions.*;
import com.rest.library.mapper.AuthorMapper;
import com.rest.library.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorService.class);
    private static final String MSG = "There is no author with id: ";

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public Long addAuthor(AuthorSimpleDto authorSimpleDto) {
        String firstName = authorSimpleDto.getFirstName();
        String lastName = authorSimpleDto.getLastName();
        Author author = authorRepository.save(new Author(firstName, lastName));
        LOGGER.info("Author successful added with id: " + author.getId());
        return author.getId();
    }

    public AuthorDto getAuthor(Long id) throws AuthorNotFoundException {
        if (authorRepository.findById(id).isPresent()) {
            return authorMapper.mapToAuthorDto(authorRepository.findById(id).get());
        } else {
            LOGGER.error(MSG + id);
            throw new AuthorNotFoundException(MSG + id);
        }
    }

    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> authorDtos = new ArrayList<>();
        authorRepository.findAll().forEach(a -> authorDtos.add(authorMapper.mapToAuthorDto(a)));
        return authorDtos;
    }

    public void deleteAuthor(Long id) throws AuthorCantBeDeletedException, AuthorNotFoundException {
        if (authorRepository.findById(id).isPresent()) {
            if (authorRepository.findById(id).get().getBooks().isEmpty()) {
                authorRepository.deleteById(id);
                LOGGER.info("Author with id " + id + " successful deleted.");
            } else {
                LOGGER.error("Author has assigned books and can't be deleted");
                throw new AuthorCantBeDeletedException("Author has assigned books and can't be deleted");
            }
        } else {
            LOGGER.error(MSG + id);
            throw new AuthorNotFoundException(MSG + id);
        }
    }
}
