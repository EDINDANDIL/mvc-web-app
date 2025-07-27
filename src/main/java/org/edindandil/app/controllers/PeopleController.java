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

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int userId, Model model) {
        model.addAttribute("person", personDAO.show(userId))
                .addAttribute("relatedBooks", personDAO.getRelatedBooks(userId));
        return "people/show";
    }

    @GetMapping("/new")
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

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int userId) {

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
