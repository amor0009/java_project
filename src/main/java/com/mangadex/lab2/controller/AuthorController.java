package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.service.author.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
public class AuthorController {
    private AuthorService authorService;

    @GetMapping(value = "list")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }
    @GetMapping(value = "find/{authorId}", produces = "application/json")
    public Author getAuthorById(@PathVariable String authorId) {
        return authorService.getAuthorById(authorId);
    }
    @PostMapping("save")
    public Author addAuthor(@RequestBody Author author) {
        return authorService.addAuthor(author);
    }
    @PutMapping("update")
    public Author updateAuthor(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }
    @DeleteMapping("delete/{authorId}")
    public void deleteAuthor(@PathVariable String authorId) {
        authorService.deleteAuthorById(authorId);
    }
}
