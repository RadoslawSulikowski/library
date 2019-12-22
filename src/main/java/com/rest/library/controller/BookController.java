package com.rest.library.controller;

import com.rest.library.domain.BookDto;
import com.rest.library.domain.BookSimplyDto;
import com.rest.library.exceptions.*;
import com.rest.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(method = RequestMethod.POST, value = "addBook")
    public void addBook(@RequestBody BookSimplyDto bookSimplyDto) throws AuthorNotFoundException {
        bookService.addBook(bookSimplyDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBook")
    public BookDto getBook(@RequestParam Long id) throws BookNotFoundException {
        return bookService.getBook(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllBooks")
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteBook")
    public void deleteBook(Long id) throws BookNotFoundException, BookCantBeDeletedException {
        bookService.deleteBook(id);
    }
}
