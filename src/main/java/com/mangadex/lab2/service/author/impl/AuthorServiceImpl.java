package com.mangadex.lab2.service.author.impl;

import com.mangadex.lab2.aspects.AspectAnnotation;
import com.mangadex.lab2.cache.LRUCacheAuthor;
import com.mangadex.lab2.cache.LRUCacheManga;
import com.mangadex.lab2.exceptions.BadRequestException;
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
    private static final String NO_AUTHOR = "Author doesn't exist with that id = ";
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    public Author getAuthorById(String id) {
        Optional<Author> author = lruCacheAuthor.get(id);
        if (author.isEmpty()) {
            author = authorRepository.findById(id);
            if (author.isEmpty()) {
                return null;
            }
            lruCacheAuthor.put(author.get().getId(), author.get());
        }
        return author.get();
    }
    @Override
    public Author addAuthor(Author author) {
        if (Boolean.TRUE.equals(authorRepository.existsById(author.getId())))
            throw new BadRequestException(NO_AUTHOR + author.getId());
        if (author.getId() == null || author.getName() == null || author.getType() == null)
            throw new BadRequestException("Fields [id, name, type] have to be provided");
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
