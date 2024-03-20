package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.service.AuthorServices.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
public class AuthorController {
    private AuthorService authorService;

    @GetMapping(value = "")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
    @GetMapping(value = "/{authorId}", produces = "application/json")
    public Author getAuthorById(@PathVariable String authorId) {
        return authorService.getAuthorById(authorId);
    }
    @PostMapping("")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }
    @PatchMapping("")
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }
    @DeleteMapping("/{authorId}")
    public void deleteAuthor(@PathVariable String authorId) {
        authorService.deleteAuthorById(authorId);
    }
}
