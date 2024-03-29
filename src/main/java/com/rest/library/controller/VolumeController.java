package com.rest.library.controller;

import com.rest.library.domain.VolumeDto;
import com.rest.library.domain.VolumeCreationDto;
import com.rest.library.domain.VolumeModificationDto;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.VolumeCantBeDeletedException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.exceptions.VolumeStatusCantBeChangedException;
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
    public void addVolume(@RequestBody VolumeCreationDto volumeCreationDto) throws BookNotFoundException {
        volumeService.addVolume(volumeCreationDto);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getNumberOfAvailableVolumesOfBook")
    public int getNumberOfAvailableVolumesOfBook(@RequestParam Long bookId) throws BookNotFoundException {
        return volumeService.getNumberOfAvailableVolumesOfBook(bookId);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "changeVolumeStatus")
    public VolumeDto changeVolumeStatus(@RequestBody VolumeModificationDto volumeModificationDto)
            throws VolumeNotFoundException, VolumeStatusCantBeChangedException {
        return volumeService.changeVolumeStatus(volumeModificationDto);
    }


    @RequestMapping(method = RequestMethod.GET, value = "getAllVolumes")
    public List<VolumeDto> getAllVolumes() {
        return volumeService.getAllVolumes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "getVolume")
    public VolumeDto getVolume(@RequestParam Long id) throws VolumeNotFoundException {
        return volumeService.getVolume(id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteVolume")
    public void deleteVolume(@RequestParam Long id) throws VolumeCantBeDeletedException, VolumeNotFoundException {
        volumeService.deleteVolume(id);
    }
}
