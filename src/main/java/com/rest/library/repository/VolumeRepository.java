package com.rest.library.repository;

import com.rest.library.domain.Volume;
import org.springframework.data.repository.CrudRepository;

public interface VolumeRepository extends CrudRepository<Volume, Long> {
}
