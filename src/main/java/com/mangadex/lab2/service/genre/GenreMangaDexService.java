package com.mangadex.lab2.service.genre;

import com.fasterxml.jackson.databind.JsonNode;
import com.mangadex.lab2.cache.Cache;
import com.mangadex.lab2.model.Genre;
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
public class GenreMangaDexService {
    private final WebClient webClient = WebClient
            .builder()
            .baseUrl("https://api.mangadex.org/manga/tag")
            .build();

    private Cache cache;

    public List<Genre> getAllGenres() {
        JsonNode genreResponse = webClient.get()
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (genreResponse == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<Genre> genreList = new ArrayList<>();
        for (JsonNode element: genreResponse.findValue("data")) {
            Genre genre = new Genre();
            genre.setId(element.findValue("id").toPrettyString()
                    .substring(1, element.findValue("id").toPrettyString().length() - 1));
            genre.setType(element.findValue("group").toPrettyString()
                    .substring(1, element.findValue("group").toPrettyString().length() - 1));
            genre.setName(element.findValue("attributes").findValue("name").findValue("en").toPrettyString()
                    .substring(1, element.findValue("attributes").findValue("name").findValue("en").toPrettyString().length() - 1));
            genreList.add(genre);
            String key = "GENRE ID " + genre.getId();
            cache.put(key, genre);
        }
        log.info("new information is added to database");
        return genreList;
    }
}
