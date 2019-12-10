package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Reader")
@Table(name = "reader")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    String firstName;
    String lastName;

    @Column(name = "creationDate", updatable = false, nullable = false)
    LocalDateTime creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Borrowing> borrowings = new ArrayList<>();

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Reader addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
        borrowing.setReader(this);
        return this;
    }

    public Reader removeBorrowing(Borrowing borrowing) {
        borrowings.remove(borrowing);
        borrowing.setReader(null);
        return this;
    }
}
