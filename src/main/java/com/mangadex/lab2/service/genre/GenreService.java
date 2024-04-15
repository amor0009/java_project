package com.mangadex.lab2.service.genre;

import com.mangadex.lab2.model.Genre;
import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    List<Genre> saveAllGenres(List<Genre> genres);

    Genre saveGenre(Genre genre);

    Genre findGenreById(String id);

    Genre updateGenre(Genre genre);

    void deleteGenre(String id);
}
