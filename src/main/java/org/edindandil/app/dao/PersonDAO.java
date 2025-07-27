package org.edindandil.app.dao;

import org.edindandil.app.models.Book;
import org.edindandil.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate; 
    
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Optional<Person> show(String personName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE personName = ?", new Object[]{personName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int userId) {
        return jdbcTemplate.query("SELECT * FROM person WHERE userId = ?", new Object[]{userId},new BeanPropertyRowMapper<>(Person.class))
                .stream().findFirst().orElse(null); 
    }

    public Integer getIdByName(String personName) {
        return jdbcTemplate.queryForObject(
                "SELECT userId FROM person WHERE personName = ?",
                new Object[]{personName},
                Integer.class
        );
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(personName, dateOfBirth) VALUES (?, ?)", person.getPersonName(), person.getDateOfBirth());
    }

    public void update(int userId, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET personName = ?, dateOfBirth = ? WHERE userId = ?",  updatedPerson.getPersonName(), updatedPerson.getDateOfBirth(), userId);
    }

    public void delete(int userId) {
        jdbcTemplate.update("DELETE FROM person WHERE userId = ?", userId);
    }

    public List<Book> getRelatedBooks(int userId) {
        return jdbcTemplate.query("SELECT * FROM books INNER JOIN person ON books.userId = person.userId WHERE books.userId = ?",  new Object[]{userId}, new BeanPropertyRowMapper<>(Book.class));
    }
}
