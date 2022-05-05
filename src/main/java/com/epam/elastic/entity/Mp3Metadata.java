package com.epam.elastic.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Mp3Metadata   implements Serializable {
    private  Long id;
    private  String name;
    private  int year;
    private String notes;
    private String album;
    private List<String> genre;
    private List<String> artist;

    public Mp3Metadata(Long id, String name, int year, String notes, String album, List<String> genre) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.album = album;
        this.genre = genre;
    }

    public Mp3Metadata(Long id, String name, int year, String notes, String album, List<String> genre, List<String> artist) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.notes = notes;
        this.album = album;
        this.genre = genre;
        this.artist = artist;
    }

    public Mp3Metadata() {

    }
}
