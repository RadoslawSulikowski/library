package com.rest.library.mapper;

import com.rest.library.domain.Borrowing;
import com.rest.library.domain.Volume;
import com.rest.library.domain.VolumeDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VolumeMapper {

    public VolumeDto mapToVolumeDto(Volume volume) {
        return new VolumeDto(
                volume.getId(),
                volume.getBook().getId(),
                volume.getStatus(),
                volume.getBorrowings().stream().map(Borrowing::getId).collect(Collectors.toList())
        );
    }
}
