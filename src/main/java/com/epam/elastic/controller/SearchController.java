package com.epam.elastic.controller;

import com.epam.elastic.document.Song;
import com.epam.elastic.document.SongIdList;
import com.epam.elastic.document.SongList;
import com.epam.elastic.service.IndexingService;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class SearchController {

    @Autowired
    private IndexingService indexingService;

    @GetMapping("/search/byname")
    public List<Long> searchByName(@RequestParam String name){

        return indexingService.searchHitIdsByName(name);
    }
    @GetMapping("/search/byalbum")
    public List<Long> searchByAlbum(@RequestParam String name){
        return indexingService.searchHitIdsByAlbum(name);
    }

    @GetMapping("/search")
    public List<Long> searchByAllFields(@RequestParam String name) throws IOException {
        return indexingService.searchHitByMultiQueryIds(name);
    }

    @GetMapping("/delete")
    public boolean searchByAllFields(@RequestParam Long id) throws IOException {
        return indexingService.removeSongById(id);
    }
}
