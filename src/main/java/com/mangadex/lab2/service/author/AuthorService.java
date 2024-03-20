package com.mangadex.lab2.service.author;

import com.mangadex.lab2.model.Author;
import java.util.List;

public interface AuthorService {
    List<Author> getAllAuthors();
    Author getAuthorById(String id);
    Author addAuthor(Author author);
    Author updateAuthor(Author author);
    void deleteAuthorById(String id);
}
