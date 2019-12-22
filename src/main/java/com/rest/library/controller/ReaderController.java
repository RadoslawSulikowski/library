package com.rest.library.controller;

import com.rest.library.domain.ReaderDto;
import com.rest.library.domain.ReaderSimpleDto;
import com.rest.library.exceptions.ReaderCantBeDeletedException;
import com.rest.library.exceptions.ReaderNotFoundException;
import com.rest.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reader")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @RequestMapping(method = RequestMethod.POST, value = "addReader")
    public void addReader(@RequestBody ReaderSimpleDto readerSimpleDto) {
        readerService.addReader(readerSimpleDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getReader")
    public ReaderDto getReader(@RequestParam Long id) throws ReaderNotFoundException {
        return readerService.getReader(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllReaders")
    public List<ReaderDto> getAllReaders() {
        return readerService.getAllReaders();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteReader")
    public void deleteReader(@RequestParam Long id) throws ReaderNotFoundException, ReaderCantBeDeletedException {
        readerService.deleteReader(id);
    }
}
