package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Borrowing")
@Table(name = "borrowing")
public class Borrowing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volume_id")
    Volume volume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reader_id")
    Reader reader;

    @Column(name = "borrowingDate", updatable = false, nullable = false)
    LocalDateTime borrowingDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    LocalDateTime returningDate;

    public Borrowing(Volume volume, Reader reader) {
        this.volume = volume;
        this.reader = reader;
    }
}
