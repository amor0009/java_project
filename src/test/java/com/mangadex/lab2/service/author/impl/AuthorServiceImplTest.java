package com.mangadex.lab2.service.author.impl;

import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {
    @InjectMocks
    private AuthorServiceImpl authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private Cache cache;

    @Test
    void testSave() {
        Author author = new Author();
        author.setName("author name test");
        author.setId("ID");
        author.setType("test");
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        Assertions.assertEquals(author, authorService.addAuthor(author));
    }

    @Test
    void testFindById_Exists() {
        Author author = new Author();
        author.setName("author name test");
        author.setId("ID");
        Mockito.when(authorRepository.findAuthorById("ID")).thenReturn(author);
        Assertions.assertEquals(author, authorService.getAuthorById("ID"));
    }

    @Test
    void testFindById_InCache() {
        Author author = new Author();
        author.setName("author name test");
        author.setId("ID");
        Mockito.when(cache.get("AUTHOR ID ID")).thenReturn(author);
        Assertions.assertEquals(author, authorService.getAuthorById("ID"));
    }

    @Test
    void testFindAll() {
        List<Author> authorList = new ArrayList<>();
        Mockito.when(authorRepository.findAll()).thenReturn(authorList);
        Assertions.assertEquals(authorList, authorService.getAllAuthors());
    }

    @Test
    void testDeleteById_Exists() {
        authorService.deleteAuthorById("ID");
        Mockito.verify(authorRepository, Mockito.times(1)).deleteById("ID");
    }

    @Test
    void testDeleteById_InCache() {
        Mockito.when(cache.containsKey("AUTHOR ID ID")).thenReturn(true);
        authorService.deleteAuthorById("ID");
        Mockito.verify(cache, Mockito.times(1)).remove("AUTHOR ID ID");
    }

    @Test
    void testUpdateGenre() {
        Author author = new Author();
        author.setName("author name test");
        author.setId("ID");
        author.setType("test");
        Mockito.when(authorRepository.save(author)).thenReturn(author);
        Assertions.assertEquals(author, authorService.updateAuthor(author));
    }

    @Test
    void testUpdateGenre_InCache() {
        Author author = new Author();
        author.setName("author name test");
        author.setId("ID");
        author.setType("test");
        String key = "AUTHOR ID " + author.getId();
        Mockito.when(cache.containsKey(key)).thenReturn(true);
        authorService.updateAuthor(author);
        Mockito.verify(cache, Mockito.times(1)).remove(key);
        Mockito.verify(cache, Mockito.times(1)).put(key, author);
    }
}
