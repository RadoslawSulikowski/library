package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "READERS")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "READER_ID")
    Long id;
    String firstName;
    String lastName;
    LocalDate creationDate;

    @OneToMany(
            targetEntity = Borrowing.class,
            cascade = CascadeType.PERSIST,
            mappedBy = "reader",
            orphanRemoval = true
    )
    List<Borrowing> borrowings = new ArrayList<>();
}
