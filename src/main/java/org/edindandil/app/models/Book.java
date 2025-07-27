package org.edindandil.app.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Book {

    private int bookId;
    @NotNull(message = "Название книги не может быть пустым")
    private String bookName;
    @NotNull(message = "Имя автора не может быть пустым")
    private String author;
    @Min(value = 0, message = "Год не может быть отрицательным")
    private int year;

    public Book(int bookId, String bookName, String author, int year) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.year = year;
    }

    public Book() {}


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
