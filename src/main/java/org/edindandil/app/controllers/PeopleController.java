package org.edindandil.app.controllers;

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

    // Spring создаст бин personDAO (он помечен как Component), затем внедрит его сюда
    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") // Мы можем при переходе указать параметр id
    // Соответственно вот эту переменную извлечем из URL
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    // В данный момент создавать объект через @ModelAttribute("person") Person person нужно,
    // чтобы spring привязал форму thymeleaf (поля) к данному объекту, чтобы при нажатии create было понятно, куда присвоить эти значения
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person) {
        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    /*
    Внутри поля ввода (так как работают только Get и Post запросы) находится скрытый ввод с методом Patch,
    который нужно обработать(создать фильтр, об этом я узнаю потом). В конфиге я добавил фильтр, который позволяет обработать
    нужные мне методы.
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }
}
