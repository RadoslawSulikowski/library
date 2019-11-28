package com.rest.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "Volume")
@Table(name = "volume")
@NamedQuery(
        name = "Volume.getNumberOfAvailableVolumesOfBook",
        query = "SELECT COUNT(*) FROM Volume WHERE status='toBorrow' AND book_id=:bookId"
)

public class Volume {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    Book book;
    String status;

    @OneToMany(
            mappedBy = "volume",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Borrowing> borrowings = new ArrayList<>();

    public void addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
        borrowing.setVolume(this);
    }

    public void removeBorrowing(Borrowing borrowing) {
        borrowings.remove(borrowing);
        borrowing.setVolume(null);
    }
}
