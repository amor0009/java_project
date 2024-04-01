package com.mangadex.lab2.service.manga.impl;

import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.repository.MangaRepository;
import com.mangadex.lab2.service.manga.MangaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MangaServiceImpl implements MangaService {
    private static final String CACHE_INFO_GET = "Cached data taken for key ";
    private static final String CACHE_INFO_REMOVE = "Cached data removed for key ";
    private static final String CACHE_INFO_UPDATE = "Cached data updated for key ";
    private static final String MANGA_KEY = "MANGA ID ";

    private final MangaRepository mangaRepository;
    private Cache cache;

    @Override
    public List<Manga> getMangas() {
        log.info("all information is obtained from database by author");
        return mangaRepository.findAll();
    }

    @Override
    public Manga saveManga(Manga manga) {
        log.info("new information is added to database");
        return mangaRepository.save(manga);
    }

    @Override
    public Manga findByID(String id) {
        String key = MANGA_KEY + id;
        Manga manga = (Manga) cache.get(key);
        if (manga != null) {
            String logMessage = CACHE_INFO_GET + key;
            log.info(logMessage);
            return manga;
        }
        manga = mangaRepository.findMangaById(id);
        cache.put(key, manga);
        log.info("information is obtained from database");
        return manga;
    }

    @Override
    public Manga findByName(String titleName) {
        String key = "MANGA NAME" + titleName;
        Manga manga = (Manga) cache.get(key);
        if (manga != null) {
            String logMessage = CACHE_INFO_GET + key;
            log.info(logMessage);
            return manga;
        }
        manga = mangaRepository.findMangaByName(titleName);
        cache.put(key, manga);
        log.info("information is obtained from database");
        return manga;
    }

    @Override
    @Transactional
    public void deleteManga(String id) {
        String key = MANGA_KEY + id;
        if (cache.containsKey(key)) {
            cache.remove(key);
            String logMessage = CACHE_INFO_REMOVE + key;
            log.info(logMessage);
            log.info("information in the cache has been deleted");
        }
        mangaRepository.deleteById(id);
        log.info("information in the database has been deleted");
    }

    @Override
    public Manga updateManga(Manga manga) {
       String key = MANGA_KEY + manga.getId();
       if (cache.containsKey(key)) {
           cache.remove(key);
           cache.put(key, manga);
           String logMessage = CACHE_INFO_UPDATE + key;
           log.info(logMessage);
           log.info("information in the cache has been updated");
       }
       log.info("information in the database has been updated");
       return mangaRepository.save(manga);
    }
}
