package com.example.wordlyweek.model;

import javax.persistence.*;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.wordlyweek.model.*;

@Entity
@Table(name = "writer")
public class Writer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int writerId;

    @Column(name = "name")
    private String writerName;

    @Column(name = "bio")
    private String bio;

    @ManyToMany
    @JoinTable(name = "writer_magazine", joinColumns = @JoinColumn(name = "writerid"), inverseJoinColumns = @JoinColumn(name = "magazineid"))
    @JsonIgnoreProperties("writers")
    private List<Magazine> magazines = new ArrayList<>();

    // Constructors
    public Writer() {
    }

    public Writer(String name, String bio) {
        this.writerName = name;
        this.bio = bio;
    }

    // Getters and Setters
    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String name) {
        this.writerName = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Magazine> getMagazines() {
        return magazines;
    }

    public void setMagazines(List<Magazine> magazines) {
        this.magazines = magazines;
    }
}
