package com.epam.elastic.service;

import com.epam.elastic.document.Song;
import com.epam.elastic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    private SongRepository repository;

    public void save(final Song person) {
        repository.save(person);
    }

    public Song findById(final String id) {
        return repository.findById(id).orElse(null);
    }
}
