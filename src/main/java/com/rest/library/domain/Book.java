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
@Entity(name = "Book")
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue
    Long id;
    String title;
    LocalDate publicationYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    Author author;

    @OneToMany(
            mappedBy = "book",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Volume> volumes = new ArrayList<>();

    public void addVolume(Volume volume){
        volumes.add(volume);
        volume.setBook(this);
    }

    public void removeBook(Volume volume){
        volumes.remove(volume);
        volume.setBook(null);
    }
}
