package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.service.CounterService;
import com.mangadex.lab2.service.author.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/author")
@AllArgsConstructor
@Slf4j
public class AuthorController {
    private AuthorService authorService;
    private CounterService counterService;
    private static final String INCREMENT = "Incremented COUNTER to  ";

    @GetMapping(value = "list")
    public List<Author> getAllAuthors() {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return authorService.getAllAuthors();
    }

    @GetMapping(value = "find/{authorId}", produces = "application/json")
    public ResponseEntity<Author> getAuthorById(@PathVariable String authorId) {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return ResponseEntity.ok(authorService.getAuthorById(authorId));
    }

    @PostMapping("save")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return new ResponseEntity<>(authorService.addAuthor(author), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Author> updateAuthor(@RequestBody Author author) {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return new ResponseEntity<>(authorService.updateAuthor(author), HttpStatus.OK);
    }

    @DeleteMapping("delete/{authorId}")
    public HttpStatus deleteAuthor(@PathVariable String authorId) {
        authorService.deleteAuthorById(authorId);
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return HttpStatus.OK;
    }
}
