package com.mangadex.lab2.service.genre.impl;

import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.repository.GenreRepository;
import com.mangadex.lab2.service.genre.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    public Genre findGenreById(String id) {
        return genreRepository.findGenreById(id);
    }

    @Override
    public Genre updateGenre(Genre genre) {
        return genreRepository.save(genre);
    }
    @Override
    public List<Genre> saveAllGenres(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }
    @Override
    public void deleteGenre(String id) {
        genreRepository.deleteById(id);
    }
}