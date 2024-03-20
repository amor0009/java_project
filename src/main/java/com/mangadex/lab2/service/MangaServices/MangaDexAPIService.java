package com.mangadex.lab2.service.MangaServices;

import com.fasterxml.jackson.databind.JsonNode;
import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.model.Genre;
import com.mangadex.lab2.model.Manga;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MangaDexAPIService {
    private final WebClient webClient;
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
                        .queryParam("title", titleName)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if(!responseManga.isEmpty()) {
            for(JsonNode manga: responseManga.findValue("data")) {
                if(manga.findValue("title").findValue("en").toPrettyString().substring(1, manga.findValue("title").findValue("en").toPrettyString().length() - 1).equals(titleName))
                    if (manga.findValue("id") != null)
                        return manga.findValue("id").toPrettyString().substring(1, manga.findValue("id").toPrettyString().length() - 1);
                    else
                        return null;
        }
        }
        return null;
    }
    public String getAuthorId(JsonNode mangaResponse) {
        String authorId = null;
        for(JsonNode value: mangaResponse.findValues("relationships")) {
            if(!value.isEmpty()) {
                authorId = value.findValue("id").toPrettyString()
                        .substring(1, value.findValue("id").toPrettyString().length() - 1);
                break;
            }
        }

        return authorId;
    }
    public List<Genre> getGenresInfo(JsonNode mangaResponse) {
        List<Genre> genres = new ArrayList<>();
        if(mangaResponse != null) {
            for(JsonNode element: mangaResponse.findValue("tags")) {
                Genre genre = new Genre();
                genre.setId(element.findValue("id").toPrettyString()
                        .substring(1, element.findValue("id").toPrettyString().length() - 1));
                genre.setType(element.findValue("group").toPrettyString()
                        .substring(1, element.findValue("group").toPrettyString().length() - 1));
                genre.setName(element.findValue("attributes").findValue("name").findValue("en").toPrettyString()
                        .substring(1, element.findValue("attributes").findValue("name").findValue("en").toPrettyString().length() - 1));
                genres.add(genre);
            }
        }

        return genres;
    }
    public Author getAuthorInfo(@NonNull String authorId) {
        JsonNode authorResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/author/" + authorId)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        Author author = new Author();
        author.setName(authorResponse.findValue("attributes").findValue("name").toPrettyString()
                .substring(1,authorResponse.findValue("attributes").findValue("name").toPrettyString().length() - 1));
        author.setType(authorResponse.findValue("type").toPrettyString()
                .substring(1, authorResponse.findValue("type").toPrettyString().length() - 1));
        author.setId(authorId);
        return author;
    }
    public Manga getMangaInfo(@NonNull String mangaId) {
        String authorId;
        JsonNode mangaResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/manga/"+ mangaId)
                        .build()
                )
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        authorId = getAuthorId(mangaResponse);
        Author author = getAuthorInfo(authorId);
        List<Genre> genres = getGenresInfo(mangaResponse);

        Manga manga = new Manga();
        manga.setTitle(mangaResponse.findValue("title").findValue("en").toPrettyString()
                .substring(1, mangaResponse.findValue("title").findValue("en").toPrettyString().length() -1));
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
        return manga;
    }
}
