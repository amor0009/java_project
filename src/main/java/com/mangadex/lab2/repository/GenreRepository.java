package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, String> {
    Genre findGenreById(String id);
}
