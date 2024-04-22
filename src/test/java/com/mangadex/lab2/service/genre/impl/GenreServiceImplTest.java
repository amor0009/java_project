package com.mangadex.lab2.service.genre.impl;

import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.repository.GenreRepository;
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
class GenreServiceImplTest {
    @InjectMocks
    private GenreServiceImpl genreService;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private Cache cache;

    @Test
    void testSave() {
        Genre genre = new Genre();
        genre.setName("genre name test");
        genre.setId("ID");
        genre.setType("test");
        Mockito.when(genreRepository.save(genre)).thenReturn(genre);
        Assertions.assertEquals(genre, genreService.saveGenre(genre));
    }

    @Test
    void testFindById_Exists() {
        Genre genre = new Genre();
        genre.setName("genre name test");
        genre.setId("ID");
        Mockito.when(genreRepository.findGenreById("ID")).thenReturn(genre);
        Assertions.assertEquals(genre, genreService.findGenreById("ID"));
    }

    @Test
    void testFindById_InCache() {
        Genre genre = new Genre();
        genre.setName("genre name test");
        genre.setId("ID");
        Mockito.when(cache.get("GENRE ID ID")).thenReturn(genre);
        Assertions.assertEquals(genre, genreService.findGenreById("ID"));
    }

    @Test
    void testFindAll() {
        List<Genre> genreList = new ArrayList<>();
        Mockito.when(genreRepository.findAll()).thenReturn(genreList);
        Assertions.assertEquals(genreList, genreService.getAllGenres());
    }

    @Test
    void testDeleteById_Exists() {
        genreService.deleteGenre("ID");
        Mockito.verify(genreRepository, Mockito.times(1)).deleteById("ID");
    }

    @Test
    void testDeleteById_InCache() {
        Mockito.when(cache.containsKey("GENRE ID ID")).thenReturn(true);
        genreService.deleteGenre("ID");
        Mockito.verify(cache, Mockito.times(1)).remove("GENRE ID ID");
    }

    @Test
    void testUpdateGenre() {
        Genre genre = new Genre();
        genre.setName("genre name test");
        genre.setId("ID");
        genre.setType("test");
        Mockito.when(genreRepository.save(genre)).thenReturn(genre);
        Assertions.assertEquals(genre, genreService.updateGenre(genre));
    }

    @Test
    void testUpdateGenre_InCache() {
        Genre genre = new Genre();
        genre.setName("genre name test");
        genre.setId("ID");
        genre.setType("test");
        String key = "GENRE ID " + genre.getId();
        Mockito.when(cache.containsKey(key)).thenReturn(true);
        genreService.updateGenre(genre);
        Mockito.verify(cache, Mockito.times(1)).remove(key);
        Mockito.verify(cache, Mockito.times(1)).put(key, genre);
    }
}