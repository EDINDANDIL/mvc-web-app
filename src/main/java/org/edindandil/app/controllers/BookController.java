package org.edindandil.app.controllers;

import jakarta.validation.Valid;
import org.edindandil.app.dao.BookDAO;
import org.edindandil.app.dao.PersonDAO;
import org.edindandil.app.models.Book;
import org.edindandil.app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, JdbcTemplate jdbcTemplate) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}") 
    public String show(@PathVariable("id") int bookId, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(bookId))
                .addAttribute("people", personDAO.index())
                .addAttribute("owner", bookDAO.getOwnerById(bookId));

        return "books/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "books/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int bookId) {
        model.addAttribute("book", bookDAO.show(bookId));
        return "books/edit";
    }


    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int bookId) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String  makeOwner(@PathVariable("id") int bookId, @RequestParam("userId") int userId) {
        bookDAO.makeOwner(bookId,  userId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/remove")
    public String removeOwner(@PathVariable("id") int bookId) {
        bookDAO.removeOwner(bookId);
        return "redirect:/books";
    }
}
