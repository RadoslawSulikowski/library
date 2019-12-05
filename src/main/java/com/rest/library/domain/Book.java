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
@Entity(name = "Book")
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    Long id;

    String title;
    int publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    Author author;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Volume> volumes = new ArrayList<>();

    public Book(String title, int publicationYear, Author author) {
        this.title = title;
        this.publicationYear = publicationYear;
        this.author = author;
    }

    public Book addVolume(Volume volume) {
        volumes.add(volume);
        volume.setBook(this);
        return this;
    }

    public Book removeVolume(Volume volume) {
        volumes.remove(volume);
        volume.setBook(null);
        return this;
    }
}
