package com.rest.library.controller;

import com.rest.library.domain.BorrowingDto;
import com.rest.library.domain.BorrowingBorrowDto;
import com.rest.library.domain.BorrowingReturnDto;
import com.rest.library.exceptions.*;
import com.rest.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @RequestMapping(method = RequestMethod.POST, value = "borrowVolume")
    public void borrowVolume(@RequestBody BorrowingBorrowDto borrowingBorrowDto)
            throws VolumeNotFoundException, ReaderNotFoundException, VolumeCantBeBorrowedException {
        borrowingService.borrowVolume(borrowingBorrowDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "returnVolume")
    public void returnVolume(@RequestBody BorrowingReturnDto borrowingReturnDto)
            throws VolumeNotFoundException, VolumeAlreadyReturnedException, BorrowingNotFoundException {
        borrowingService.returnVolume(borrowingReturnDto);
    }


    @RequestMapping(method = RequestMethod.GET, value = "getAllBorrowings")
    public List<BorrowingDto> getAllBorrowings() {
        return borrowingService.getAllBorrowings();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getBorrowing")
    public BorrowingDto getBorrowing(@RequestParam Long borrowingId) throws BorrowingNotFoundException {
        return borrowingService.getBorrowing(borrowingId);
    }
}
