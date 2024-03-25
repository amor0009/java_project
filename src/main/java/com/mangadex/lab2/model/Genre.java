package com.mangadex.lab2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "genres")
public class Genre {
    @Id
    private String id;
    private String name;
    private String type;
    @ManyToMany(mappedBy = "genres", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private List<Manga> mangas;
}
