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
@Entity(name = "Author")
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue
    Long id;

    String firstName;
    String lastName;

    @OneToMany(
            mappedBy = "author",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    List<Book> books = new ArrayList<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
        return this;
    }

    public Author removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
        return this;
    }
}
