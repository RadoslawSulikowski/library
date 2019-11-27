package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Volume;
import com.rest.library.domain.VolumeDto;
import com.rest.library.exceptions.BookNotFoundException;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.VolumeNotFoundException;
import com.rest.library.repository.BookRepository;
import com.rest.library.repository.VolumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VolumeMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(VolumeMapper.class);

    @Autowired
    BookRepository bookRepository;

    @Autowired
    VolumeRepository volumeRepository;

    @Autowired
    private BorrowingMapper borrowingMapper;

    public VolumeDto mapToVolumeDto(Volume volume) {
        return new VolumeDto(
                volume.getId(),
                volume.getBook().getId(),
                volume.getStatus(),
                volume.getBorrowings().stream().map(Borrowing::getId).collect(Collectors.toList())
        );
    }

    public Volume mapToVolume(VolumeDto volumeDto) throws BookNotFoundException, BorrowingNotFoundException {
        if (!bookRepository.findById(volumeDto.getBookId()).isPresent()) {
            LOGGER.error("Can not map VolumeDto to Volume - Book with id " + volumeDto.getBookId() + " doesn't exist!");
            throw new BookNotFoundException("Book with id " + volumeDto.getBookId() + " doesn't exist!");
        } else {
            return new Volume(
                    volumeDto.getId(),
                    bookRepository.findById(volumeDto.getBookId()).get(),
                    volumeDto.getStatus(),
                    borrowingMapper.mapToBorrowingList(volumeDto.getBorrowingIdList())
            );
        }
    }

    public List<Volume> mapToVolumeList(List<Long> volumeIds) throws VolumeNotFoundException {
        List<Volume> volumes = new ArrayList<>();
        for (Long volumeId : volumeIds) {
            if (volumeRepository.findById(volumeId).isPresent()) {
                volumes.add(volumeRepository.findById(volumeId).get());
            } else {
                LOGGER.error("Can not map VolumeIdList to VolumeList - " +
                        "Volume with id " + volumeId + " doesn't exist!");
                throw new VolumeNotFoundException("Volume with id " + volumeId + " doesn't exist!");
            }
        }
        return volumes;
    }
}
