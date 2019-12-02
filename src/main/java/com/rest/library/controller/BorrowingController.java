package com.rest.library.controller;

import com.rest.library.domain.BorrowingDto;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.ReaderNotFoundException;
import com.rest.library.exceptions.VolumeAlreadyReturnedException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.service.BorrowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/v1/borrowing")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    @RequestMapping(method = RequestMethod.POST, value = "borrowVolume")
    public void borrowVolume(@RequestParam @NotNull Long volumeId, @RequestParam @NotNull Long readerId)
            throws VolumeNotFoundException, ReaderNotFoundException {
        borrowingService.borrowVolume(volumeId, readerId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "returnVolume")
    public void returnVolume(@RequestParam Long volumeId)
            throws VolumeNotFoundException, VolumeAlreadyReturnedException, BorrowingNotFoundException {
        borrowingService.returnVolume(volumeId);
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
