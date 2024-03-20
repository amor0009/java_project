package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
}
