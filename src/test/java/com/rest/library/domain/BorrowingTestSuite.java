package com.rest.library.domain;

import com.rest.library.repository.BorrowingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BorrowingTestSuite {

    @Autowired
    BorrowingRepository borrowingRepository;

    @Test
    public void testSaveNewBorrowing() {
        //Given

        //When
        Borrowing borrowing = borrowingRepository.save(new Borrowing());
        LocalDateTime borrowingDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        Long borrowingId = borrowing.getId();
        LocalDateTime resultBorrowingDate = borrowing.getBorrowingDate();

        //Then
        Assert.assertTrue(borrowingRepository.findById(borrowingId).isPresent());
        Assert.assertEquals(borrowingDate, resultBorrowingDate);


        //CleanUp
        borrowingRepository.deleteById(borrowingId);
    }

}
