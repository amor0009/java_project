package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.service.genre.GenreMangaDexService;
import com.mangadex.lab2.service.genre.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/genre")
@AllArgsConstructor
public class GenreController {
    private GenreMangaDexService genreMangaDexService;
    private GenreService genreService;

    @GetMapping(value = "list")
    public List<Genre> getAllGenres() {
        return genreService.getAllGenres();
    }

    @GetMapping(value = "update")
    public ResponseEntity<List<Genre>> updateGenres() {
        List<Genre> genres = genreMangaDexService.getAllGenres();
        if (genres.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        genreService.saveAllGenres(genres);
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping(value = "find/{genreId}")
    public ResponseEntity<Genre> getGenreById(@PathVariable String genreId) {
        return ResponseEntity.ok(genreService.findGenreById(genreId));
    }

    @PostMapping(value = "save")
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        if(genre == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(genreService.saveGenre(genre), HttpStatus.OK);
    }

    @PutMapping(value = "update")
    public ResponseEntity<Genre> updateGenre(@RequestBody Genre genre) {
        return new ResponseEntity<>(genreService.updateGenre(genre), HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{genreId}")
    public HttpStatus deleteGenre(@PathVariable String genreId) {
        genreService.deleteGenre(genreId);
        return HttpStatus.OK;
    }
}
