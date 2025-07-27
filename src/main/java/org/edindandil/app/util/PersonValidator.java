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

    // В этом методе надо указать, на объектах какого класса можно использовать этот валидатор
    // Так как сам метод validate принимает класс Object, то по сути туда можно подать все что угодно,
    // а это недопустимо. В методе supports надо указать поддерживаемые классы
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz); // проверяем соответствие подаваемого класса и поддерживаемого
    }

    // Тут непосредственно валидация
    @Override
    public void validate(Object target,  Errors errors) {
        Person person = (Person) target;
        Optional<Person> result = personDAO.show(person.getPersonName());

        /* есть ли человек в бд с таким же email?
        Если есть, то этот метод его найдет и ответ будет не null
        я в методе указал Optional, поэтому могу пользоваться isPresent(),
        что является лучшей проверкой, чем != null
        */
        if (result.isPresent()) {
                errors.rejectValue("personName", "", "Имя уже есть в списке");
            /* Обращаемся к объекту, который собирает ошибки, произвести метод rejectValue.
            В нем должно быть 3 параметра:
             1) поле, по которому произошла ошибка
             2) код ошибки (пока не указываю)
             3) сообщение ошибки
            */
        }
    }
}
