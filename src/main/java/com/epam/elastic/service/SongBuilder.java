package com.epam.elastic.service;

import com.epam.elastic.document.Song;
import com.epam.elastic.entity.Mp3Metadata;
import org.springframework.stereotype.Service;

@Service
public interface SongBuilder {

    Song create(Mp3Metadata mp3Metadata);
}
