package com.rest.library.service;

import com.rest.library.domain.BorrowingDto;
import com.rest.library.exceptions.BorrowingNotFoundException;
import com.rest.library.mapper.BorrowingMapper;
import com.rest.library.repository.BorrowingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class BorrowingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BorrowingService.class);
    private static final String MSG = "There is no Borrowing with id ";

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private BorrowingMapper borrowingMapper;

    public List<BorrowingDto> getAllBorrowings() {
        List<BorrowingDto> borrowingDtos = new ArrayList<>();
        borrowingRepository.findAll().forEach(b -> borrowingDtos.add(borrowingMapper.mapToBorrowingDto(b)));
        return borrowingDtos;
    }

    public BorrowingDto getBorrowing(Long id) throws BorrowingNotFoundException {
        if (borrowingRepository.findById(id).isPresent()) {
            return borrowingMapper.mapToBorrowingDto(borrowingRepository.findById(id).get());
        } else {
            LOGGER.error(MSG + id);
            throw new BorrowingNotFoundException(MSG + id);
        }
    }
}
