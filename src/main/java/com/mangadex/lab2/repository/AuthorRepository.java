package com.mangadex.lab2.repository;

import com.mangadex.lab2.model.Author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {
    Author findAuthorById(String id);
}
