package com.mangadex.lab2.service.genre.impl;

import com.mangadex.lab2.cache.LRUCacheGenre;
import com.mangadex.lab2.cache.LRUCacheManga;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.repository.GenreRepository;
import com.mangadex.lab2.service.genre.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private LRUCacheGenre cacheGenre;
    private LRUCacheManga cacheManga;
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre saveGenre(Genre genre) {
        Genre newGenre = genreRepository.save(genre);
        cacheGenre.put(newGenre.getId(), newGenre);
        return newGenre;
    }

    @Override
    public Genre findGenreById(String id) {
        Genre genre = genreRepository.findGenreById(id);
        cacheGenre.put(genre.getId(), genre);
        return genre;
    }

    @Override
    public Genre updateGenre(Genre genre) {
        Genre newGenre = genreRepository.save(genre);
        for(Manga manga: newGenre.getMangas()) {
            cacheManga.remove(manga.getId());
        }
        cacheGenre.put(newGenre.getId(), newGenre);
        return newGenre;
    }
    @Override
    public List<Genre> saveAllGenres(List<Genre> genres) {
        return genreRepository.saveAll(genres);
    }
    @Override
    public void deleteGenre(String id) {
        Genre genre = genreRepository.findGenreById(id);
        if(genre != null) {
            List<String> mangasId = genreRepository.deleteGenreByIdAndReturnMangasId(id);
            for(String mangaId: mangasId) {
                cacheManga.remove(mangaId);
            }
            cacheGenre.remove(id);
            genreRepository.deleteById(id);
        }
    }
}