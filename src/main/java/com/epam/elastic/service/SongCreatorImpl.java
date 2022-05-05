package com.epam.elastic.service;

import com.epam.elastic.document.Song;
import com.epam.elastic.entity.Mp3Metadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SongCreatorImpl implements SongBuilder{

    @Override
    public Song create(Mp3Metadata mp3Metadata) {
        Song song = new Song();
        song.setId(mp3Metadata.getId());
        song.setName(mp3Metadata.getName());
        song.setNotes(mp3Metadata.getNotes());
        song.setYear(mp3Metadata.getYear());
        song.setAlbum(mp3Metadata.getAlbum());
        song.setGenres(mp3Metadata.getGenre());
        song.setArtists(mp3Metadata.getArtist());
        return song;
    }
}
