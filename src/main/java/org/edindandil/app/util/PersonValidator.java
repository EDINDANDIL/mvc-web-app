package org.edindandil.app.util;


import org.edindandil.app.dao.PersonDAO;
import org.edindandil.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz); // проверяем соответствие подаваемого класса и поддерживаемого
    }

    @Override
    public void validate(Object target,  Errors errors) {
        Person person = (Person) target;
        Optional<Person> result = personDAO.show(person.getPersonName());
        if (result.isPresent()) {
                errors.rejectValue("personName", "", "Имя уже есть в списке");
        
        }
    }
}
