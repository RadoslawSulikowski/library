package com.rest.library.repository;

import com.rest.library.domain.Borrowing;
import org.springframework.data.repository.CrudRepository;

public interface BorrowingRepository extends CrudRepository<Borrowing, Long> {
}
