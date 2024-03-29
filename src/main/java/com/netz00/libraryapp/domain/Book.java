package com.netz00.libraryapp.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "book", uniqueConstraints = {@UniqueConstraint(name = "book_isbn_unique", columnNames = "isbn")})
public class Book {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "book_sequence")
    @Column(name = "id", updatable = false)
    private Long id;

    /**
     * TODO
     * strip hyphens and whitespace before inserting into
     * the database so that it will fit in 10 or 13 characters,
     * and to make search/querying easier
     */
    @Size(min = 10, max = 13, message = "ISBN must be be between 10 and 13 characters")
    @NotEmpty(message = "ISBN is required. ISBN cannot be null or empty")
    @Column(name = "isbn", nullable = false, columnDefinition = "CHAR(17)")
    private String isbn;

    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    @NotEmpty(message = "Name is required. Name cannot be null or empty")
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Publisher of this Book.
     */
    @Size(min = 0, max = 50, message = "Publisher must be be up to 50 characters max")
    @Column(name = "publisher", nullable = true)
    private String publisher;

    /**
     * Year of publishing this Book.
     */
    @Column(name = "year", nullable = true)
    private Integer year;

    /**
     * Note about this Book.
     */
    @Size(min = 0, max = 200, message = "Note must be up to 200 characters max")
    @Column(name = "note", nullable = true, columnDefinition = "TEXT")
    private String note;

    /**
     * Timestamp when this Book was inserted.
     */
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "created_date", nullable = false)
    private Instant created_date = Instant.now();

    /**
     * Timestamp when this Book was last modified.
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "last_modified_date", nullable = true)
    private Timestamp last_modified_date;

    @PreUpdate
    protected void preUpdate() {
        this.last_modified_date = new Timestamp((new Date()).getTime());
    }

    /**
     * TODO
     * Lazy doesn't work!
     */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Author author;

    public Book() {
    }

    public Book(String isbn, String name, String publisher, Integer year, String note, Author author) {
        this.isbn = isbn;
        this.name = name;
        this.publisher = publisher;
        this.year = year;
        this.note = note;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCreated_date() {
        return created_date;
    }

    public Timestamp getLast_modified_date() {
        return last_modified_date;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" + "id='" + id + '\'' + ", isbn='" + isbn + '\'' + ", name='" + name + '\'' + ", publisher='" + publisher + '\'' + ", year=" + year + ", note='" + note + '\'' + ", createdDate=" + created_date + ", lastModifiedDate=" + last_modified_date + ", author=" + author + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, name, publisher, year, author);
    }
}
