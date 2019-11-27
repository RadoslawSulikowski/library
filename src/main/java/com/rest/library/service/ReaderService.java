package com.rest.library.service;

import com.rest.library.domain.Reader;
import com.rest.library.domain.ReaderDto;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.exceptions.ReaderAlreadyExistException;
import com.rest.library.exceptions.ReaderCantBeDeletedException;
import com.rest.library.exceptions.ReaderNotFoundException;
import com.rest.library.mapper.ReaderMapper;
import com.rest.library.repository.ReaderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReaderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReaderService.class);
    private static final String MSG = "There is no reader with id: ";

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderMapper readerMapper;

    public void addReader(ReaderDto readerDto) throws BorrowingNotFoundException, ReaderAlreadyExistException {
        if (readerDto.getId() != null && readerRepository.findById(readerDto.getId()).isPresent()) {
            LOGGER.error("Can not add Reader - " +
                    "Reader with id " + readerDto.getId() + " already exist!");
            throw new ReaderAlreadyExistException("Reader with id " + readerDto.getId() + " already exist!");
        }
        Reader reader = readerRepository.save(readerMapper.mapToReader(readerDto));
        LOGGER.info("Reader successful added with id: " + reader.getId());
    }

    public ReaderDto getReader(Long id) throws ReaderNotFoundException {
        if (readerRepository.findById(id).isPresent()) {
            return readerMapper.mapToReaderDto(readerRepository.findById(id).get());
        } else {
            LOGGER.error(MSG + id);
            throw new ReaderNotFoundException(MSG + id);
        }
    }

    public List<ReaderDto> getAllReaders() {
        List<ReaderDto> readerDtos = new ArrayList<>();
        readerRepository.findAll().forEach(r -> readerDtos.add(readerMapper.mapToReaderDto(r)));
        return readerDtos;
    }

    public void deleteReader(Long id) throws ReaderNotFoundException, ReaderCantBeDeletedException {
        if (readerRepository.findById(id).isPresent()) {
            if (readerRepository.findById(id).get().getBorrowings().isEmpty()) {
                readerRepository.deleteById(id);
                LOGGER.info("Reader with id " + id + " was successful deleted.");
            } else {
                LOGGER.error("Reader has assigned borrowings and can't be deleted");
                throw new ReaderCantBeDeletedException("Reader has assigned borrowings");
            }
        } else {
            LOGGER.error(MSG + id);
            throw new ReaderNotFoundException(MSG + id);
        }
    }
}
