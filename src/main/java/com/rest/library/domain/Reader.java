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
@Entity(name = "Reader")
@Table(name = "reader")
public class Reader {
    @Id
    @GeneratedValue
    Long id;
    String firstName;
    String lastName;
    LocalDate creationDate;

    @OneToMany(
            mappedBy = "reader",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Borrowing> borrowings = new ArrayList<>();

    public void addBorrowing(Borrowing borrowing){
        borrowings.add(borrowing);
        borrowing.setReader(this);
    }

    public void removeBorrowing(Borrowing borrowing) {
        borrowings.remove(borrowing);
        borrowing.setReader(null);
    }
}
