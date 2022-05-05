package com.epam.elastic.service;

import com.epam.elastic.document.Song;
import com.epam.elastic.helper.Indices;
import com.epam.elastic.repository.SongRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@Service
@Slf4j
public class IndexingSongServiceImpl implements IndexingService{

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private RestHighLevelClient client;

    private ElasticsearchRestTemplate template;

    private final String index = Indices.SONG_INDEX;


    @Autowired
    public IndexingSongServiceImpl(ElasticsearchRestTemplate template) {
        this.template = template;
    }

    @Override
    public void save(Song song) {
        songRepository.save(song);
    }

    @Override
    public Page<Song> searchByName(String name) {
        Page<Song> songPage
                =   songRepository.findAll( PageRequest.of(0, 10));
        return songPage;
    }

    @Override
    public SearchHits<Song> searchHitByName(String name){
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("name", ".*"+name+".*"))
                .build();
        SearchHits<Song> song =
                template.search(searchQuery, Song.class, IndexCoordinates.of(index));

            return song;
    }

    @Override
    public SearchHits<Song> searchHitByAlbum(String album){
        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("album", ".*"+album+".*"))
                .build();
        SearchHits<Song> song =
                template.search(searchQuery, Song.class, IndexCoordinates.of(index));

        return song;
    }

    private  SearchResponse multiSearch(String searchStr) throws IOException {
        String allField = "*";
        MultiMatchQueryBuilder mmqb = QueryBuilders.multiMatchQuery(searchStr, allField);
        mmqb.operator(Operator.OR);
        mmqb.type("cross_fields");
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(mmqb);
        searchRequest.source(searchSourceBuilder);
        return client.search(searchRequest,RequestOptions.DEFAULT);

    }

    @Override
    public List<Song> searchHitByMultiQuery(String searchStr) throws IOException {
        SearchResponse searchResponse = multiSearch(searchStr);
        List<Song> songs = new ArrayList<>();
        ObjectMapper objectMapper =new ObjectMapper();
        for (SearchHit searchHit : searchResponse.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            sourceAsMap.remove("_class");
           Song songFromHits = objectMapper.convertValue(sourceAsMap, Song.class);
           songs.add(songFromHits);
        }
            return songs;
    }

    @Override
    public List<Long> searchHitByMultiQueryIds(String searchStr) throws IOException {

        return  searchHitByMultiQuery(searchStr).stream().map(s->s.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Long> MultiSearch(String query) throws IOException {

        return  searchHitByMultiQuery(query).stream().map(x->x.getId()).collect(Collectors.toList());
    }

    @Override
    public List<Long> searchHitIdsByAlbum(String name) {

        return searchHitByAlbum(name).stream().map(x->x.getContent().getId()).collect(Collectors.toList());
    }


    @Override
    public void aggregation() throws IOException {
        SearchRequest request = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.aggregation(AggregationBuilders.terms("name_").field("name")
                .subAggregation(AggregationBuilders.terms("year_").field("year")));

        request.source(searchSourceBuilder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        Terms nameTerm=search.getAggregations().get("name_");

        for(Terms.Bucket classroomBucket:nameTerm.getBuckets()){
            Terms genderTerm = classroomBucket.getAggregations().get("year_");
            for (Terms.Bucket genderBucket:genderTerm.getBuckets()){
                System.out.println("genre:"+classroomBucket.getKeyAsString()+"name:"+genderBucket.getKeyAsString()+"count:"+genderBucket.getDocCount());
            }
        }

    }

    @Override
    public List<Long> searchHitIdsByName(String name) {
//        List<Long> songIdList = new ArrayList<>();
        SearchHits<Song> songSearchHits = searchHitByName(name);
        return songSearchHits.getSearchHits().stream().map(x->x.getContent().getId()).collect(Collectors.toList());
//        return songIdList;
    }

    @Override
    public boolean removeSongById(Long id) throws IOException {
        DeleteRequest request = new DeleteRequest(index,id.toString());
        DeleteResponse deleteResponse = client.delete(
                request, RequestOptions.DEFAULT);
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            return false;
        }
        return true;
    }

}
