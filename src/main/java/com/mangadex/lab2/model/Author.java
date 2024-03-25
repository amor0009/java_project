package com.mangadex.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Authors")
public class Author {
    private String name;
    @Id
    @Column(unique = true)
    private String id;
    private String type;
    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Manga> mangas;
}
