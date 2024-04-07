package com.example.wordlyweek.model;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "magazine")
public class Magazine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int magazineId;

    @Column(name = "title")
    private String magazineName;

    @Column(name = "publicationdate")
    private String publicationDate;

    @ManyToMany(mappedBy = "magazines")
    @JsonIgnoreProperties("magazines")
    private List<Writer> writers = new ArrayList<>();

    // Constructors
    public Magazine() {
    }

    public Magazine(String title, String publicationDate) {
        this.magazineName= title;
        this.publicationDate = publicationDate;
    }

    // Getters and Setters
    public int getMagazineId() {
        return magazineId;
    }

    public void setMagazineId(int magazineId) {
        this.magazineId = magazineId;
    }

    public String getTitle() {
        return magazineName;
    }

    public void setTitle(String title) {
        this.magazineName = title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }
}
