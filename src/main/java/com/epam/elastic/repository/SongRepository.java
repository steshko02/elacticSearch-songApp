package com.epam.elastic.repository;

import com.epam.elastic.document.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends ElasticsearchRepository<Song,String> {
//     Page<Song> findByName(String name, PageRequest of);
}
