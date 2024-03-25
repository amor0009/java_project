package com.mangadex.lab2.service.author.impl;

import com.mangadex.lab2.cache.LRUCacheAuthor;
import com.mangadex.lab2.cache.LRUCacheManga;
import com.mangadex.lab2.exceptions.ResourceNotFoundException;
import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.repository.AuthorRepository;
import com.mangadex.lab2.service.author.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    private LRUCacheAuthor lruCacheAuthor;
    private LRUCacheManga lruCacheManga;
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    public Author getAuthorById(String id)
            throws ResourceNotFoundException {
        Optional<Author> author = lruCacheAuthor.get(id);
        if (author.isEmpty()) {
            author = authorRepository.findById(id);
            if (author.isEmpty()) {
                throw new ResourceNotFoundException("There is no author with such id" + id);
            }
            lruCacheAuthor.put(author.get().getId(), author.get());
        }
        return author;
    }
    @Override
    public Author addAuthor(Author author) {
        authorRepository.save(author);
        lruCacheAuthor.put(author.getId(), author);
        return author;
    }
    @Override
    public Author updateAuthor(Author author) {
        Author newAuthor = authorRepository.save(author);
        lruCacheAuthor.put(newAuthor.getId(), newAuthor);
        return newAuthor;
    }
    @Override
    public void deleteAuthorById(String id) {
        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()) {
            lruCacheAuthor.remove(id);
            for(Manga manga: author.get().getMangas())
                lruCacheManga.remove(manga.getId());
            authorRepository.deleteById(id);
        }
    }
}
