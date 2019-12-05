package com.rest.library.service;

import com.rest.library.domain.*;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.VolumeCantBeDeletedException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.mapper.VolumeMapper;
import com.rest.library.repository.BookRepository;
import com.rest.library.repository.VolumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class VolumeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeService.class);
    private static String MSG = "There is no Volume with id ";

    @Autowired
    private VolumeMapper volumeMapper;

    @Autowired
    private VolumeRepository volumeRepository;

    @Autowired
    private BookRepository bookRepository;

    public Long addVolume(Long bookId, String status) throws BookNotFoundException {
        if (bookRepository.findById(bookId).isPresent()) {
            Book book = bookRepository.findById(bookId).get();
            Volume volume = volumeRepository.save(new Volume(book, status));
            bookRepository.save(book.addVolume(volume));
            LOGGER.info("Volume successful added with id: " + volume.getId());
            return volume.getId();
        } else {
            LOGGER.error("Can't add Volume - there is no Book with id " + bookId);
            throw new BookNotFoundException("Can't add Volume - there is no Book with id " + bookId);
        }
    }

    public int getNumberOfAvailableVolumesOfBook(Long bookId) throws BookNotFoundException {
        if (bookRepository.findById(bookId).isPresent()) {
            return volumeRepository.getNumberOfAvailableVolumesOfBook(bookId);
        } else {
            LOGGER.error("There is no book with id " + bookId);
            throw new BookNotFoundException("There is no book with id " + bookId);
        }
    }

    public VolumeDto changeVolumeStatus(Long volumeId, String newStatus) throws VolumeNotFoundException {
        if (volumeRepository.findById(volumeId).isPresent()) {
            Volume volume = volumeRepository.findById(volumeId).get();
            volume.setStatus(newStatus);
            return volumeMapper.mapToVolumeDto(volumeRepository.save(volume));
        } else {
            LOGGER.error(MSG + volumeId);
            throw new VolumeNotFoundException(MSG + volumeId);
        }
    }

    public VolumeDto getVolume(Long id) throws VolumeNotFoundException {
        if (volumeRepository.findById(id).isPresent()) {
            return volumeMapper.mapToVolumeDto(volumeRepository.findById(id).get());
        } else {
            LOGGER.error(MSG + id);
            throw new VolumeNotFoundException(MSG + id);
        }
    }

    public List<VolumeDto> getAllVolumes() {
        List<VolumeDto> volumeDtos = new ArrayList<>();
        volumeRepository.findAll().forEach(v -> volumeDtos.add(volumeMapper.mapToVolumeDto(v)));
        return volumeDtos;
    }

    public void deleteVolume(Long id) throws VolumeNotFoundException, VolumeCantBeDeletedException {
        if (volumeRepository.findById(id).isPresent()) {
            if (volumeRepository.findById(id).get().getBorrowings().isEmpty()) {
                bookRepository.save(volumeRepository.findById(id).get().getBook().removeVolume(volumeRepository.findById(id).get()));
                volumeRepository.deleteById(id);
                LOGGER.info("Volume with id " + id + " was successful deleted.");
            } else {
                LOGGER.error("Volume has borrowings and can't be deleted.");
                throw new VolumeCantBeDeletedException("Volume has borrowings and can't be deleted.");
            }
        } else {
            LOGGER.error(MSG + id);
            throw new VolumeNotFoundException(MSG + id);
        }
    }
}
