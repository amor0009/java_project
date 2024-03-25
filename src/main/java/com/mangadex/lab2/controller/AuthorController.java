package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.service.author.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Author> getAuthorById(@PathVariable String authorId) {
        return ResponseEntity.ok(authorService.getAuthorById(authorId));
    }

    @PostMapping("save")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorService.addAuthor(author), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        return new ResponseEntity<>(authorService.updateAuthor(author), HttpStatus.OK);
    }

    @DeleteMapping("delete/{authorId}")
    public HttpStatus deleteAuthor(@PathVariable String authorId) {
        authorService.deleteAuthorById(authorId);
        return HttpStatus.OK;
    }
}
