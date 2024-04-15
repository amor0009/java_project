package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.service.manga.MangaDexAPIService;
import com.mangadex.lab2.service.manga.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/manga")
@AllArgsConstructor
public class MangaController {
    private MangaService mangaService;
    private MangaDexAPIService mangaDexAPIService;

    @GetMapping(value = "manga_dex")
    public ResponseEntity<Manga> addMangaFromMangaDex(@RequestParam String titleName) {
        Manga manga = mangaService.findByName(titleName);
        if (manga != null) {
            return new ResponseEntity<>(manga, HttpStatus.OK);
        }
        String mangaID = mangaDexAPIService.getMangaId(titleName);
        manga =  mangaDexAPIService.getMangaInfo(mangaID);
        mangaService.saveManga(manga);
        return new ResponseEntity<>(manga, HttpStatus.OK);
    }

    @GetMapping("list")
    public List<Manga> getMangas() {
        return mangaService.getMangas();
    }

    @GetMapping("find_by_id/{id}")
    public ResponseEntity<Manga> getMangaByID(@PathVariable String id) {
        return ResponseEntity.ok(mangaService.findByID(id));
    }

    @GetMapping("find_by_name")
    public ResponseEntity<Manga> getMangaByName(@RequestParam String titleName) {
        return ResponseEntity.ok(mangaService.findByName(titleName));
    }

    @PostMapping("save")
    public ResponseEntity<Manga> saveManga(@RequestBody Manga manga) {
        if (manga == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(mangaService.saveManga(manga), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Manga> updateManga(@RequestBody Manga manga) {
        return new ResponseEntity<>(mangaService.updateManga(manga), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteManga(@PathVariable String id) {
        mangaService.deleteManga(id);
        return HttpStatus.OK;
    }
}