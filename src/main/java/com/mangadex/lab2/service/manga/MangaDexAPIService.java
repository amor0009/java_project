package com.mangadex.lab2.service.manga;

import com.fasterxml.jackson.databind.JsonNode;
import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.model.Manga;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MangaDexAPIService {
    private static final String TITLE1= "title";
    private static final String ATTRIBUTES1 = "attributes";

    private final WebClient webClient;
    private Cache cache;

    MangaDexAPIService() {
        this.webClient = WebClient
                .builder()
                .baseUrl("https://api.mangadex.org")
                .build();
    }

    public String getMangaId(String titleName) {
        JsonNode responseManga =  webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/manga")
                        .queryParam(TITLE1, titleName)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (responseManga != null) {
            for(JsonNode manga: responseManga.findValue("data")) {
                if(manga.findValue(TITLE1).findValue("en").toPrettyString().substring(1, manga.findValue(TITLE1).findValue("en").toPrettyString().length() - 1).equals(titleName)
                        && manga.findValue("id") != null)
                    return manga.findValue("id").toPrettyString().substring(1, manga.findValue("id").toPrettyString().length() - 1);
            }
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return null;
    }

    public String getAuthorId(JsonNode mangaResponse) {
        String authorId = null;
        if (mangaResponse == null) {
            for(JsonNode value: mangaResponse.findValues("relationships")) {
                if(!value.isEmpty()) {
                    authorId = value.findValue("id").toPrettyString()
                            .substring(1, value.findValue("id").toPrettyString().length() - 1);
                    break;
                }
            }
            if (authorId == null)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return authorId;
    }

    public List<Genre> getGenresInfo(JsonNode mangaResponse) {
        List<Genre> genres = new ArrayList<>();
        if (mangaResponse != null) {
            for(JsonNode element: mangaResponse.findValue("tags")) {
                Genre genre = new Genre();
                genre.setId(element.findValue("id").toPrettyString()
                        .substring(1, element.findValue("id").toPrettyString().length() - 1));
                genre.setType(element.findValue("group").toPrettyString()
                        .substring(1, element.findValue("group").toPrettyString().length() - 1));
                genre.setName(element.findValue(ATTRIBUTES1).findValue("name").findValue("en").toPrettyString()
                        .substring(1, element.findValue(ATTRIBUTES1).findValue("name").findValue("en").toPrettyString().length() - 1));
                genres.add(genre);
                cache.put(genre.getId(), genre);
            }
        } else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        log.info("new information is added to database");
        return genres;
    }

    public Author getAuthorInfo(String authorId) {
        if (authorId == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        JsonNode authorResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/author/" + authorId)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (authorResponse == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Author author = new Author();
        author.setName(authorResponse.findValue(ATTRIBUTES1).findValue("name").toPrettyString()
                .substring(1,authorResponse.findValue(ATTRIBUTES1).findValue("name").toPrettyString().length() - 1));
        author.setType(authorResponse.findValue("type").toPrettyString()
                .substring(1, authorResponse.findValue("type").toPrettyString().length() - 1));
        author.setId(authorId);
        cache.put(authorId, author);
        log.info("new information is added to database");
        return author;
    }

    public Manga getMangaInfo(String mangaId) {
        if (mangaId == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        String authorId;
        JsonNode mangaResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/manga/"+ mangaId)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        if (mangaResponse == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        authorId = getAuthorId(mangaResponse);
        Author author = getAuthorInfo(authorId);
        List<Genre> genres = getGenresInfo(mangaResponse);

        Manga manga = new Manga();
        manga.setTitle(mangaResponse.findValue(TITLE1).findValue("en").toPrettyString()
                .substring(1, mangaResponse.findValue(TITLE1).findValue("en").toPrettyString().length() -1));
        manga.setId(mangaResponse.findValue("id").toPrettyString()
                .substring(1, mangaResponse.findValue("id").toPrettyString().length() - 1));
        manga.setType(mangaResponse.findValue("type").toPrettyString()
                .substring(1,mangaResponse.findValue("type").toPrettyString().length() - 1));
        manga.setStatus(mangaResponse.findValue("status").toPrettyString()
                .substring(1, mangaResponse.findValue("status").toPrettyString().length() - 1));
        manga.setLastVolume(mangaResponse.findValue("lastVolume").toPrettyString()
                .substring(1, mangaResponse.findValue("lastVolume").toPrettyString().length() - 1));
        manga.setLastChapter(mangaResponse.findValue("lastChapter").toPrettyString()
                .substring(1, mangaResponse.findValue("lastChapter").toPrettyString().length() - 1));
        manga.setYear(mangaResponse.findValue("year").asInt());
        manga.setAuthor(author);
        manga.setGenres(genres);
        cache.put(mangaId, manga);
        log.info("new information is added to database");
        return manga;
    }
}
