package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class VolumeDto {
    Long id;
    Long bookId;
    String status;
    List<Long> borrowingIdList = new ArrayList<>();
}
