package com.rest.library.mapper;

import com.rest.library.domain.Volume;
import com.rest.library.domain.VolumeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VolumeMapper {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BorrowingMapper borrowingMapper;

    public VolumeDto mapToVolumeDto(Volume volume) {
        return new VolumeDto(
                volume.getId(),
                bookMapper.mapToBookDto(volume.getBook()),
                volume.getStatus(),
                borrowingMapper.mapToBorrowingDtoList(volume.getBorrowings())
        );
    }

    public Volume mapToVolume(VolumeDto volumeDto) {
        return new Volume(
                volumeDto.getId(),
                bookMapper.mapToBook(volumeDto.getBookDto()),
                volumeDto.getStatus(),
                borrowingMapper.mapToBorrowingList(volumeDto.getBorrowingDtoList())
        );
    }

    public List<VolumeDto> mapToVolumeDtoList(List<Volume> volumes) {
        List<VolumeDto> volumeDtos = new ArrayList<>();
        volumes.forEach(v -> volumeDtos.add(mapToVolumeDto(v)));
        return volumeDtos;
    }

    public List<Volume> mapToVolumeList(List<VolumeDto> volumeDtos) {
        List<Volume> volumes = new ArrayList<>();
        volumeDtos.forEach(v -> volumes.add(mapToVolume(v)));
        return volumes;
    }

}
