package com.mangadex.lab2.service.genre.impl;

import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.repository.GenreRepository;
import com.mangadex.lab2.service.genre.GenreService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private static final String CACHE_INFO_GET = "Cached data taken for key ";
    private static final String CACHE_INFO_REMOVE = "Cached data removed for key ";
    private static final String CACHE_INFO_UPDATE = "Cached data updated for key ";
    private static final String GENRE_KEY = "GENRE ID ";

    private final GenreRepository genreRepository;
    private Cache cache;

    @Override
    public List<Genre> getAllGenres() {
        log.info("all information is obtained from database");
        return genreRepository.findAll();
    }

    @Override
    public Genre saveGenre(Genre genre) {
        log.info("new information is added to database");
        return genreRepository.save(genre);
    }

    @Override
    public Genre findGenreById(String id) {
        String key = GENRE_KEY + id;
        Genre genre = (Genre) cache.get(key);
        if (genre != null) {
            String logMessage = CACHE_INFO_GET + key;
            log.info(logMessage);
            return genre;
        }
        genre = genreRepository.findGenreById(id);
        cache.put(key, genre);
        log.info("information is obtained from database");
        return genre;
    }

    @Override
    public Genre updateGenre(Genre genre) {
        String key = GENRE_KEY + genre.getId();
        if (cache.containsKey(key)) {
            cache.remove(key);
            cache.put(key, genre);
            String logMessage = CACHE_INFO_UPDATE + key;
            log.info(logMessage);
            log.info("information in the cache has been updated");
        }
        log.info("information in the database has been updated");
        return genreRepository.save(genre);
    }

    @Override
    public List<Genre> saveAllGenres(List<Genre> genres) {
        log.info("new information is added to database");
        return genreRepository.saveAll(genres);
    }

    @Override
    @Transactional
    public void deleteGenre(String id) {
        String key = GENRE_KEY + id;
        if (cache.containsKey(key)) {
            cache.remove(key);
            String logMessage = CACHE_INFO_REMOVE + key;
            log.info(logMessage);
            log.info("information in the cache has been deleted");
        }

        genreRepository.deleteGenreByIdAndReturnMangaIds(id);
        genreRepository.deleteById(id);
        log.info("information in the database has been deleted");
    }
}