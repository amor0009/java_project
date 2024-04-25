package com.mangadex.lab2.service.manga.impl;

import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.repository.MangaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;


@ExtendWith(MockitoExtension.class)
class MangaServiceImplTest {
    @InjectMocks
    private MangaServiceImpl mangaService;

    @Mock
    private MangaRepository mangaRepository;

    @Mock
    private Cache cache;

    @Test
    void testSave() {
        Manga manga = new Manga();
        manga.setTitle("manga name test");
        manga.setStatus("test");
        manga.setType("test");
        manga.setId("ID");
        manga.setLastChapter("test");
        manga.setLastVolume("test");
        manga.setYear(2001);
        Mockito.when(mangaRepository.save(manga)).thenReturn(manga);
        Assertions.assertEquals(manga, mangaService.saveManga(manga));
    }

    @Test
    void testFindById_Exists() {
        Manga manga = new Manga();
        manga.setId("ID");
        manga.setTitle("manga name test");
        Mockito.when(mangaRepository.findMangaById("ID")).thenReturn(manga);
        Assertions.assertEquals(manga, mangaService.findByID("ID"));
    }

    @Test
    void testFindById_InCache() {
        Manga manga = new Manga();
        manga.setId("ID");
        manga.setTitle("manga name test");
        Mockito.when(cache.get("MANGA ID ID")).thenReturn(manga);
        Assertions.assertEquals(manga, mangaService.findByID("ID"));
    }

    @Test
    void testFindByName_Exists() {
        Manga manga = new Manga();
        manga.setId("ID");
        manga.setTitle("manga name test");
        Mockito.when(mangaRepository.findMangaByName("manga name test")).thenReturn(manga);
        Assertions.assertEquals(manga, mangaService.findByName("manga name test"));
    }

    @Test
    void testFindByName_InCache() {
        Manga manga = new Manga();
        manga.setId("ID");
        manga.setTitle("manga name test");
        Mockito.when(cache.get("MANGA NAME manga name test")).thenReturn(manga);
        Assertions.assertEquals(manga, mangaService.findByName("manga name test"));
    }


    @Test
    void testFindAll() {
        List<Manga> mangaList = new ArrayList<>();
        Mockito.when(mangaRepository.findAll()).thenReturn(mangaList);
        Assertions.assertEquals(mangaList, mangaService.getMangas());
    }

    @Test
    void testDeleteById_Exists() {
        mangaService.deleteManga("ID");
        Mockito.verify(mangaRepository, Mockito.times(1)).deleteById("ID");
    }

    @Test
    void testDeleteById_InCache() {
        Mockito.when(cache.containsKey("MANGA ID ID")).thenReturn(true);
        mangaService.deleteManga("ID");
        Mockito.verify(cache, Mockito.times(1)).remove("MANGA ID ID");
    }

    @Test
    void testUpdateManga() {
        Manga manga = new Manga();
        manga.setTitle("manga name test");
        manga.setStatus("test");
        manga.setType("test");
        manga.setId("ID");
        manga.setLastChapter("test");
        manga.setLastVolume("test");
        manga.setYear(2001);
        Mockito.when(mangaRepository.save(manga)).thenReturn(manga);
        Assertions.assertEquals(manga, mangaService.updateManga(manga));
    }

    @Test
    void testUpdateManga_InCache() {
        Manga manga = new Manga();
        manga.setTitle("manga name test");
        manga.setStatus("test");
        manga.setType("test");
        manga.setId("ID");
        manga.setLastChapter("test");
        manga.setLastVolume("test");
        manga.setYear(2001);
        String key = "MANGA ID " + manga.getId();
        Mockito.when(cache.containsKey(key)).thenReturn(true);
        mangaService.updateManga(manga);
        Mockito.verify(cache, Mockito.times(1)).remove(key);
        Mockito.verify(cache, Mockito.times(1)).put(key, manga);
    }

    @Test
    void testBulkInsert_success() {
        List<Manga> mangaList = new ArrayList<>();
        mangaList.add(new Manga());
        Mockito.when(mangaRepository.saveAll(mangaList)).thenReturn(mangaList);
        Assertions.assertEquals(mangaList, mangaService.bulkInsert(mangaList));
    }

    @Test
    void testBulkInsert_failed() {
        List<Manga> mangaList = new ArrayList<>();
        Mockito.when(mangaRepository.saveAll(mangaList)).thenReturn(mangaList);
        Assertions.assertEquals(null, mangaService.bulkInsert(mangaList));
    }
}