package com.mangadex.lab2.service.manga;

import com.mangadex.lab2.model.Author;
import com.mangadex.lab2.model.Manga;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MangaDexAPIServiceTest {

    @InjectMocks
    private MangaDexAPIService mangaDexAPIService;

    @Test
    void testGetMangaInfo() {
        Manga manga = mangaDexAPIService.getMangaInfo("a1c7c817-4e59-43b7-9365-09675a149a6f");
        Assertions.assertEquals("a1c7c817-4e59-43b7-9365-09675a149a6f", manga.getId());
        Assertions.assertEquals("One Piece", manga.getTitle());
    }

    @Test
    void testGetMangaId() {
        String id = mangaDexAPIService.getMangaId("One Piece");
        Assertions.assertEquals("a1c7c817-4e59-43b7-9365-09675a149a6f", id);
    }

    @Test
    void testGetAuthorInfo() {
        Author author = mangaDexAPIService.getAuthorInfo("b6045e2c-28f4-4ce0-b4dd-b14070f2f5ae");
        Assertions.assertEquals("Oda Eiichiro", author.getName());
    }
}
