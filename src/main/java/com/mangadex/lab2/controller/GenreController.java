package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.service.GenreServices.GenreMangaDexService;
import com.mangadex.lab2.service.GenreServices.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/genre")
@AllArgsConstructor
public class GenreController {
    private GenreMangaDexService genreMangaDexService;
    private GenreService genreService;

    @GetMapping(value = "")
    public List<Genre> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return genres;
    }
    @GetMapping(value = "/update")
    public List<Genre> updateGenres() {
        List<Genre> genres = genreMangaDexService.getAllGenres();
        return genreService.saveAllGenres(genres);
    }
    @GetMapping(value = "/{genreId}")
    public Genre getGenreById(@PathVariable String genreId){
        Genre genre = genreService.findGenreById(genreId);
        return genre;
    }
    @PostMapping(value = "")
    public Genre addGenre(@RequestBody Genre genre) {
        if(genre == null)
            return null;
        Genre newGenre = genreService.saveGenre(genre);
        return newGenre;
    }
    @PutMapping(value = "")
    public Genre updateGenre(@RequestBody Genre genre) {
        if(genre == null)
            return null;
        Genre newGenre = genreService.updateGenre(genre);
        return newGenre;
    }
    @DeleteMapping(value = "/{genreId}", produces = "application/json")
    public String deleteGenre(@PathVariable String genreId) {
        Genre genre = genreService.findGenreById(genreId);
        if(genre == null)
            return null;
        genreService.deleteGenre(genreId);
        return "deleted genre " + genreId;
    }
}
