package com.rest.library.repository;

import com.rest.library.domain.Volume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface VolumeRepository extends CrudRepository<Volume, Long> {
}
