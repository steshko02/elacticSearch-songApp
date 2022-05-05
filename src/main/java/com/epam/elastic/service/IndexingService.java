package com.epam.elastic.service;

import com.epam.elastic.document.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface IndexingService {

    void save(Song song);

    void  aggregation() throws IOException;
    Page<Song> searchByName(String name);

    SearchHits<Song> searchHitByName(String name);
    List<Long> searchHitIdsByName(String name);
    SearchHits<Song> searchHitByAlbum(String album);

    List<Long> MultiSearch(String query) throws IOException;

    List<Long> searchHitIdsByAlbum(String name);


    List<Song> searchHitByMultiQuery(String searchStr) throws IOException;
    List<Long> searchHitByMultiQueryIds(String searchStr) throws IOException;

    boolean removeSongById(Long id) throws IOException;
}