package com.mangadex.lab2.controller;

import com.mangadex.lab2.exceptions.ResourceNotFoundException;
import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.service.manga.MangaDexAPIService;
import com.mangadex.lab2.service.manga.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/manga")
@AllArgsConstructor
public class MangaController {
    private MangaService mangaService;
    private MangaDexAPIService mangaDexAPIService;

    @GetMapping(value="manga_dex")
    public ResponseEntity<Manga> addMangaFromMangaDex(@RequestParam String titleName)
            throws ResourceNotFoundException {
        String mangaID = mangaDexAPIService.getMangaId(titleName);
        Manga manga =  mangaDexAPIService.getMangaInfo(mangaID);
        mangaService.saveManga(manga);
        return new ResponseEntity<>(manga, HttpStatus.OK);
    }

    @GetMapping("list")
    public List<Manga> getMangas() {
        return mangaService.getMangas();
    }

    @GetMapping("find/{id}")
    public ResponseEntity<Manga> getMangaByID(@PathVariable String id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(mangaService.findByID(id));
    }

    @PostMapping("save")
    public ResponseEntity<Manga> saveManga(@RequestBody Manga manga)
            throws ResourceNotFoundException {
        if(manga == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>(mangaService.saveManga(manga), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Manga> updateManga(@RequestBody Manga manga)
            throws ResourceNotFoundException {
        return new ResponseEntity<>(mangaService.updateManga(manga), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteManga(@PathVariable String id) {
        mangaService.deleteManga(id);
        return HttpStatus.OK;
    }
}