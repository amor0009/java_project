package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaRepository extends JpaRepository<Manga, String> {
    void deleteById(String id);
    Manga findMangaById(String id);
}
