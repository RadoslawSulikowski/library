package com.rest.library.repository;

import com.rest.library.domain.Volume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface VolumeRepository extends CrudRepository<Volume, Long> {

    int getNumberOfAvailableVolumesOfBook(@Param("bookId") Long bookId);

}
