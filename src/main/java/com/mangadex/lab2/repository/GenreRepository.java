package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    Genre findGenreById(String id);

    @Query(value = "DELETE FROM manga_genre where genre_id =:genreId RETURNING manga_id", nativeQuery = true)
    List<String> deleteGenreByIdAndReturnMangasId(@Param("genreId") String id);
}
