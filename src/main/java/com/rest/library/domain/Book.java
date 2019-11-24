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
@Table(name = "BOOKS")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String title;
    LocalDate publicationYear;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    Author author;

    @OneToMany(
            targetEntity = Volume.class,
            mappedBy = "book",
            cascade = CascadeType.PERSIST
    )
    List<Volume> volumes = new ArrayList<>();
}
