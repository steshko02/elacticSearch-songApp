package com.epam.elastic.document;

import com.epam.elastic.helper.Indices;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Document(indexName = Indices.SONG_INDEX)
//@Setting(settingPath = "static/es-settings.json")
@Getter
@Setter
public class Song {
    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Keyword)
    private  String name;

    @Field(type = FieldType.Integer)
    private  int year;

    @Field(type = FieldType.Text)
    private String notes;

    @Field(type = FieldType.Keyword)
    private  String album;

    @Field(type = FieldType.Keyword)
    private List<String> genres;

    @Field(type = FieldType.Keyword)
    private List<String> artists;

}
