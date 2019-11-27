package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Borrowing")
@Table(name = "borrowing")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volume_id")
    Volume volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    Reader reader;

    LocalDate borrowingDate = LocalDate.now();
    LocalDate returningDate;
}
