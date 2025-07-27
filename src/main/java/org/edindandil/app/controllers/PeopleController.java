package org.edindandil.app.controllers;

import jakarta.validation.Valid;
import org.edindandil.app.util.PersonValidator;
import org.springframework.validation.BindingResult;
import org.edindandil.app.dao.PersonDAO;
import org.edindandil.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    // Spring создаст бин personDAO (он помечен как Component), затем внедрит его сюда
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") // Мы можем при переходе указать параметр id
    // Соответственно вот эту переменную извлечем из URL
    public String show(@PathVariable("id") int userId, Model model) {
        model.addAttribute("person", personDAO.show(userId))
                // хочу, чтобы метод вернул список List<Book>
                .addAttribute("relatedBooks", personDAO.getRelatedBooks(userId));
        return "people/show";
    }

    @GetMapping("/new")
    // В данный момент создавать объект через @ModelAttribute("person") Person person нужно,
    // чтобы spring привязал форму thymeleaf (поля) к данному объекту, чтобы при нажатии create было понятно, куда присвоить эти значения
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {

        personValidator.validate(person, bindingResult);

        System.out.println("Create method called");
        System.out.println("Person name: " + person.getPersonName());
        System.out.println("Errors: " + bindingResult.getAllErrors());

        if (bindingResult.hasErrors())
            return "people/new";

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int userId) {
        model.addAttribute("person", personDAO.show(userId));
        return "people/edit";
    }

    /*
    Внутри поля ввода (так как работают только Get и Post запросы) находится скрытый ввод с методом Patch,
    который нужно обработать(создать фильтр, об этом я узнаю потом). В конфиге я добавил фильтр, который позволяет обработать
    нужные мне методы.
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int userId) {

        /*
        Проблема: если редактировать имеющегося пользователя с именем name и менять ему имя,
        то метод validate сочтет это как дублирование, поэтому надо использовать валидацию тогда, когда
        id текущего пользователя и id пользователя, которого мы подали не совпадают
         */
        if (userId != personDAO.getIdByName(person.getPersonName())) {
            personValidator.validate(person,  bindingResult);
        }

        if (bindingResult.hasErrors())
            return "people/edit";

        personDAO.update(userId, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int userId) {
        personDAO.delete(userId);
        return "redirect:/people";
    }
}
