package com.mangadex.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    private String id;
    private String name;
    private String type;
    @ManyToMany(mappedBy = "genres")
    @JsonIgnore
    private Set<Manga> mangas;
}
