package com.mangadex.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "Authors")
public class Author {
    private String name;
    @Id
    @Column(unique = true)
    private String id;
    private String type;
    @OneToMany(mappedBy = "author")
    @JsonIgnore
    private Set<Manga> mangas;
}
