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
@Entity
@Table(name = "VOLUMES")
public class Volume {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    Book book;
    String status;

    @OneToMany(
            targetEntity = Borrowing.class,
            mappedBy = "volume",
            cascade = CascadeType.PERSIST
    )
    List<Borrowing> borrowings = new ArrayList<>();
}
