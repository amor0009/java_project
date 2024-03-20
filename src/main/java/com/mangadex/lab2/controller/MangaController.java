package com.mangadex.lab2.controller;

import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.service.manga.MangaDexAPIService;
import com.mangadex.lab2.service.manga.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/manga")
@AllArgsConstructor
public class MangaController {
    private MangaService mangaService;
    private MangaDexAPIService mangaDexAPIService;

    @GetMapping(value="manga_dex")
    public void addMangaFromMangaDex(@RequestParam String titleName) {
        String mangaID = mangaDexAPIService.getMangaId(titleName);
        Manga manga =  mangaDexAPIService.getMangaInfo(mangaID);
        mangaService.saveManga(manga);
    }
    @GetMapping("list")
    public List<Manga> getMangas() {
        return mangaService.getMangas();
    }
    @GetMapping("find/{id}")
    public Manga getMangaByID(@PathVariable String id) {
        return mangaService.findByID(id);
    }
    @PostMapping("save")
    public void saveManga(@RequestBody Manga manga) {
        mangaService.saveManga(manga);
    }
    @PutMapping("update")
    public void updateManga(@RequestBody Manga manga) {
        mangaService.updateManga(manga);
    }
    @DeleteMapping("delete/{id}")
    public void deleteManga(@PathVariable String id) {
        mangaService.deleteManga(id);
    }
}