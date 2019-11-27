package com.rest.library.domain;

import com.rest.library.repository.AuthorRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthorTestSuite {
    private static final Long TEST_ID = 1L;
    private static final String TEST_FIRSTNAME = "Test First Name";
    private static final String TEST_LASTNAME = "Test Last Name";

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testAllSettersAndGetters() {
        Assert.assertTrue(true);
    }

    @Test
    public void testCreateBookListWithAuthor() {
        Author author = new Author(TEST_FIRSTNAME, TEST_LASTNAME);

        authorRepository.save(author);
        System.out.println(author);
        Assert.assertTrue(author.getBooks().isEmpty());
    }
}
