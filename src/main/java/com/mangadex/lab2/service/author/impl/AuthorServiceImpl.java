package com.mangadex.lab2.service.author.impl;

import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.repository.AuthorRepository;
import com.mangadex.lab2.service.author.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {
    private static final String CACHE_INFO_GET = "Cached data taken for key ";
    private static final String CACHE_INFO_REMOVE = "Cached data removed for key ";
    private static final String CACHE_INFO_UPDATE = "Cached data updated for key ";
    private static final String AUTHOR_KEY = "AUTHOR ID ";

    private AuthorRepository authorRepository;
    private Cache cache;

    @Override
    public List<Author> getAllAuthors() {
        log.info("all information is obtained from database by author");
        return authorRepository.findAll();
    }

    @Override
    public Author getAuthorById(String id) {
        String key = AUTHOR_KEY + id;
        Author author = (Author) cache.get(key);
        if (author != null) {
            String logMessage = CACHE_INFO_GET + key;
            log.info(logMessage);
            return author;
        }
        author = authorRepository.findAuthorById(id);
        cache.put(key, author);
        log.info("information is obtained from database");
        return author;
    }

    @Override
    public Author addAuthor(Author author) {
        log.info("new information is added to database");
        return authorRepository.save(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        String key = AUTHOR_KEY + author.getId();
        if (cache.containsKey(key)) {
            cache.remove(key);
            cache.put(key, author);
            String logMessage = CACHE_INFO_UPDATE + key;
            log.info(logMessage);
            log.info("information in the cache has been updated");
        }
        log.info("information in the database has been updated");
        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthorById(String id) {
        String key = AUTHOR_KEY + id;
        if (cache.containsKey(key)) {
            cache.remove(key);
            String logMessage = CACHE_INFO_REMOVE + key;
            log.info(logMessage);
            log.info("information in the cache has been deleted");
        }
        authorRepository.deleteById(id);
        log.info("information in the database has been deleted");
    }
}

