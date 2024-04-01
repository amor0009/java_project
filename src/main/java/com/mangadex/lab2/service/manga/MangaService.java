package com.mangadex.lab2.service.manga;

import com.mangadex.lab2.model.Manga;
import java.util.List;

public interface MangaService {
    List<Manga> getMangas();
    Manga saveManga(Manga manga);
    Manga findByID(String id);
    Manga findByName(String titleName);
    void deleteManga(String id);
    Manga updateManga(Manga manga);
}