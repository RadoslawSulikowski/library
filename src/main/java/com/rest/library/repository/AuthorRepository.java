package com.rest.library.repository;

import com.rest.library.domain.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
}
