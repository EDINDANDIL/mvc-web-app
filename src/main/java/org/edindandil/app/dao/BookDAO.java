package org.edindandil.app.dao;

import org.edindandil.app.models.Book;
import org.edindandil.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM books",  new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO books(bookName, author, year) VALUES (?, ?, ?)", book.getBookName(), book.getAuthor(), book.getYear());
    }

    public Book show(int bookId) {
        return jdbcTemplate.query("SELECT * FROM books WHERE bookId = ?", new Object[]{bookId},new BeanPropertyRowMapper<>(Book.class))
                .stream().findFirst().orElse(null);
    }

    public void update(int bookId, Book updatedBook) {
        jdbcTemplate.update("UPDATE books SET bookName = ?, author = ?, year = ? WHERE bookId = ?",  updatedBook.getBookName(), updatedBook.getAuthor(), updatedBook.getYear() ,bookId);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM books WHERE bookId = ?", bookId);
    }

    public Person getOwnerById(int bookId) {
        return jdbcTemplate.query("SELECT * FROM books INNER JOIN person ON books.userId = person.userId WHERE books.bookId = ?", new Object[]{bookId},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public boolean isEmpty(int bookId) {
        return jdbcTemplate.query("select books.userid from books where bookid=?", new Object[]{bookId},new BeanPropertyRowMapper<>(Book.class)).isEmpty();
    }

    public void makeOwner(int bookId, int userId) {
        jdbcTemplate.update("UPDATE books SET userId = ?  WHERE bookId = ?", userId, bookId);
    }

    public void removeOwner(int bookId) {
        jdbcTemplate.update("UPDATE books SET userid = NULL WHERE bookid = ?", bookId);
    }
}
