package com.mangadex.lab2.service.MangaServices.ImplMangaServices;

import com.mangadex.lab2.model.Manga;
import com.mangadex.lab2.repository.MangaRepository;
import com.mangadex.lab2.service.MangaServices.MangaService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class MangaServiceImpl implements MangaService {
    private final MangaRepository repository;
    @Override
    public List<Manga> getMangas() {
        return repository.findAll();
    }
    @Override
    public void saveManga(Manga manga) { repository.save(manga); }
    @Override
    public Manga findByID(String id) {
        return repository.findMangaById(id);
    }
    @Override
    @Transactional
    public void deleteManga(String id) {
        repository.deleteById(id);
    }
    @Override
    public void updateManga(Manga manga) {
        repository.save(manga);
    }
}
