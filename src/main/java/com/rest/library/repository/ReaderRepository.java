package com.rest.library.repository;

import com.rest.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ReaderRepository extends CrudRepository<Reader, Long> {
}
