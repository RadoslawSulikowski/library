package com.rest.library.controller;

import com.rest.library.domain.AuthorDto;
import com.rest.library.exceptions.*;
import com.rest.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(method = RequestMethod.POST, value = "addAuthor")
    public void addAuthor(@RequestBody AuthorDto authorDto) throws BookNotFoundException, AuthorAlreadyExistException {
        authorService.addAuthor(authorDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAuthor")
    public AuthorDto getAuthor(@RequestParam Long id) throws AuthorNotFoundException {
        return authorService.getAuthor(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllAuthors")
    public List<AuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteAuthor")
    public void deleteAuthor(@RequestParam Long id) throws AuthorNotFoundException, AuthorCantBeDeletedException {
        authorService.deleteAuthor(id);
    }

}
