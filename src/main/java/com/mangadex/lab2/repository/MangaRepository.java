package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, String> {
    void deleteById(String id);
    Manga findMangaById(String id);
}
