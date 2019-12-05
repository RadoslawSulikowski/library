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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    Book book;

    String status;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "volume",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Borrowing> borrowings = new ArrayList<>();

    public Volume(Book book, String status) {
        this.book = book;
        this.status = status;
    }

    public Volume addBorrowing(Borrowing borrowing) {
        borrowings.add(borrowing);
        borrowing.setVolume(this);
        return this;
    }

    public Volume removeBorrowing(Borrowing borrowing) {
        borrowings.remove(borrowing);
        borrowing.setVolume(null);
        return this;
    }
}
