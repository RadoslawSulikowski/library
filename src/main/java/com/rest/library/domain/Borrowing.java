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
@Entity
@Table(name = "BORROWINGS")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "VOLUME_ID")
    Volume volume;

    @ManyToOne
    @JoinColumn(name = "READER_ID")
    Reader reader;

    LocalDate borrowingDate;
    LocalDate returningDate;

}
