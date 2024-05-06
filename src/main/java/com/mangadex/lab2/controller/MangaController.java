package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.service.CounterService;
import com.mangadex.lab2.service.manga.MangaDexAPIService;
import com.mangadex.lab2.service.manga.MangaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/manga")
@AllArgsConstructor
@Slf4j
public class MangaController {
    private MangaService mangaService;
    private MangaDexAPIService mangaDexAPIService;
    private static final String INCREMENT = "Incremented COUNTER to  ";

    @GetMapping(value = "manga_dex")
    public ResponseEntity<Manga> addMangaFromMangaDex(@RequestParam String titleName) {
        Manga manga = mangaService.findByName(titleName);
        if (manga != null) {
            log.info(INCREMENT + CounterService.incrementAndGetCount());
            return new ResponseEntity<>(manga, HttpStatus.OK);
        }
        String mangaID = mangaDexAPIService.getMangaId(titleName);
        manga =  mangaDexAPIService.getMangaInfo(mangaID);
        mangaService.saveManga(manga);
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return new ResponseEntity<>(manga, HttpStatus.OK);
    }

    @GetMapping("list")
    public List<Manga> getMangas() {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return mangaService.getMangas();
    }

    @GetMapping("find_by_id/{id}")
    public ResponseEntity<Manga> getMangaByID(@PathVariable String id) {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return ResponseEntity.ok(mangaService.findByID(id));
    }

    @GetMapping("find_by_name")
    public ResponseEntity<Manga> getMangaByName(@RequestParam String titleName) {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return ResponseEntity.ok(mangaService.findByName(titleName));
    }

    @PostMapping("save")
    public ResponseEntity<Manga> saveManga(@RequestBody Manga manga) {
        if (manga == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return new ResponseEntity<>(mangaService.saveManga(manga), HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<Manga> updateManga(@RequestBody Manga manga) {
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return new ResponseEntity<>(mangaService.updateManga(manga), HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public HttpStatus deleteManga(@PathVariable String id) {
        mangaService.deleteManga(id);
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return HttpStatus.OK;
    }

    @PostMapping(value = "saveMangas")
    public ResponseEntity<List<Manga>> saveMangas(@RequestParam String bulkParameter) {
        List<Manga> mangaList = mangaDexAPIService.findMangasInfo(mangaDexAPIService.getMangasWithName(bulkParameter));
        mangaService.bulkInsert(mangaList);
        log.info(INCREMENT + CounterService.incrementAndGetCount());
        return new ResponseEntity<>(mangaList, HttpStatus.OK);
    }
}