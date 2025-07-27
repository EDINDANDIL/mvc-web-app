package org.edindandil.app.dao;

import org.edindandil.app.models.Book;
import org.edindandil.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

// Spring создаст бин, который будет помещен в контекст и далее помещен в контроллер
@Component
public class PersonDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate; // его создали в Config, он поместиться сюда

    // Теперь весь статический блок с подключением перенесен в класс конфига
    // все работы будут производиться с классом jdbcTemplate
    public List<Person> index() {
        // Теперь весь код по извлечению вынесен в PersonMapper,
        // таким образом избегаем дублирование кода
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }


    // Optional - обертка класса. Объект может существовать, может не существовать
    // в stream надо убрать хвост .orElse(null), т.к. Optional его вернет если надо
    public Optional<Person> show(String personName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE personName = ?", new Object[]{personName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int userId) {
        // По умолчанию в нем используется PrepareStatement
        // Еще такой метод возвращает список
        return jdbcTemplate.query("SELECT * FROM person WHERE userId = ?", new Object[]{userId},new BeanPropertyRowMapper<>(Person.class))
                .stream().findFirst().orElse(null); // распоточивает список, ищет первый или нулл
        // BeanPropertyRowMapper<>(Person.class) здесь заменяет statement, из которого все данные доставали
        // и собирали в объекты Person, теперь все делается автоматом (там внутри используется PreparedStatement)
    }

    public Integer getIdByName(String personName) {
//        return jdbcTemplate.query("SELECT userId FROM person WHERE personName = ?", new Object[]{personName}, new BeanPropertyRowMapper<>(Integer.class)).stream().findFirst().orElse(-1);
        return jdbcTemplate.queryForObject(
                "SELECT userId FROM person WHERE personName = ?",
                new Object[]{personName},
                Integer.class
        );
    }

    public void save(Person person) {
        // сюда пишем update, потом запрос, и ждет параметры на места "?"
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
