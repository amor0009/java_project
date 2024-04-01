package com.mangadex.lab2.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mangas")
public class Manga {
    private String title;
    @Id
    private String id;
    private String type;
    private String status;
    private String lastVolume;
    private String lastChapter;
    private int year;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="author_id", nullable=false)
    private Author author;
    @ManyToMany (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JoinTable(name = "manga_genre",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;
}
