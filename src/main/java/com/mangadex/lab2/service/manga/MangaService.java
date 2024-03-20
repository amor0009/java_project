package com.mangadex.lab2.service.manga;

import com.mangadex.lab2.model.Manga;
import java.util.List;

public interface MangaService {
    List<Manga> getMangas();
    void saveManga(Manga manga);
    Manga findByID(String id);
    void deleteManga(String id);
    void updateManga(Manga manga);
}