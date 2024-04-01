package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    Genre findGenreById(String id);
}
