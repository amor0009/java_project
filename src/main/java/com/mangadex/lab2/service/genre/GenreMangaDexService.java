package com.mangadex.lab2.service.genre;

import com.fasterxml.jackson.databind.JsonNode;
import com.mangadex.lab2.model.Genre;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreMangaDexService {
    private final WebClient webClient;
    GenreMangaDexService() {
        webClient = WebClient
                .builder()
                .baseUrl("https://api.mangadex.org/manga/tag")
                .build();
    }
    public List<Genre> getAllGenres() {
        JsonNode genreResponse = webClient.get()
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        List<Genre> genreList = new ArrayList<>();
        Genre genre = new Genre();
        if(genreResponse != null) {
            for(JsonNode element: genreResponse.findValue("data")) {
                genre.setId(element.findValue("id").toPrettyString()
                        .substring(1, element.findValue("id").toPrettyString().length() - 1));
                genre.setType(element.findValue("group").toPrettyString()
                        .substring(1, element.findValue("group").toPrettyString().length() - 1));
                genre.setName(element.findValue("attributes").findValue("name").findValue("en").toPrettyString()
                        .substring(1, element.findValue("attributes").findValue("name").findValue("en").toPrettyString().length() - 1));
                genreList.add(genre);
            }
        }
        else
            genreList = null;
        return genreList;
    }
}
