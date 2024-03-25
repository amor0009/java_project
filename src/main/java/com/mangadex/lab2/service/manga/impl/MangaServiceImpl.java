package com.mangadex.lab2.service.manga.impl;

import com.mangadex.lab2.cache.LRUCacheAuthor;
import com.mangadex.lab2.cache.LRUCacheGenre;
import com.mangadex.lab2.cache.LRUCacheManga;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.repository.MangaRepository;
import com.mangadex.lab2.service.manga.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class MangaServiceImpl implements MangaService {
    private final MangaRepository repository;
    private LRUCacheManga cacheManga;
    private LRUCacheGenre cacheGenre;
    private LRUCacheAuthor cacheAuthor;
    @Override
    public List<Manga> getMangas() {
        return repository.findAll();
    }
    @Override
    public Manga saveManga(Manga manga) {
        repository.save(manga);
        cacheManga.put(manga.getId(), manga);
        return manga;
    }
    @Override
    public Manga findByID(String id) {
        Manga manga = repository.findMangaById(id);
        cacheManga.put(manga.getId(), manga);
        return manga;
    }
    @Override
    @Transactional
    public void deleteManga(String id) {
        Manga manga = repository.findMangaById(id);
        if(manga != null) {
            for(Genre genre: manga.getGenres()) {
                cacheGenre.remove(genre.getId());
            }
            cacheAuthor.remove(manga.getAuthor().getId());
            cacheManga.remove(id);
            repository.deleteById(id);
        }
    }
    @Override
    public Manga updateManga(Manga manga) {
        Manga newManga = repository.save(manga);
        for(Genre genre: newManga.getGenres()) {
            cacheGenre.remove(genre.getId());
        }
        cacheAuthor.remove(newManga.getAuthor().getId());
        cacheManga.put(newManga.getId(), newManga);
        return newManga;
    }
}
