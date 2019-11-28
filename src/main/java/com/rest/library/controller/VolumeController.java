package com.rest.library.controller;

import com.rest.library.domain.VolumeDto;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.VolumeCantBeDeletedException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.service.VolumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/volume")
public class VolumeController {

    @Autowired
    VolumeService volumeService;

    @RequestMapping(method = RequestMethod.POST, value = "addVolume")
    public void addVolume(@RequestBody VolumeDto volumeDto) throws BookNotFoundException, BorrowingNotFoundException {
        volumeService.addVolume(volumeDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getAllVolumes")
    public List<VolumeDto> getAllVolumes() {
        return volumeService.getAllVolumes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getVolume")
    public VolumeDto getVolume(@RequestParam Long id) throws VolumeNotFoundException {
        return volumeService.getVolume(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getNumberOfAvailableVolumesOfBook")
    public int getNumberOfAvailableVolumesOfBook(@RequestParam Long bookId) throws BookNotFoundException {
        return volumeService.getNumberOfAvailableVolumesOfBook(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "changeVolumeStatus")
    public VolumeDto changeVolumeStatus(@RequestParam String status, @RequestParam Long volumeId) throws VolumeNotFoundException {
        return volumeService.changeVolumeStatus(status, volumeId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteVolume")
    public void deleteVolume(@RequestParam Long id) throws VolumeCantBeDeletedException, VolumeNotFoundException {
        volumeService.deleteVolume(id);
    }
}
