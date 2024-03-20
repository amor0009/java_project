package com.mangadex.lab2.service.AuthorServices.ImplAuthorServices;

import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.repository.AuthorRepository;
import com.mangadex.lab2.service.AuthorServices.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    @Override
    public Author getAuthorById(String id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }
    @Override
    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }
    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.save(author);
    }
    @Override
    public void deleteAuthorById(String id) {
        authorRepository.deleteById(id);
    }
}
