package com.epam.elastic.jms;

import com.epam.elastic.document.Song;
import com.epam.elastic.entity.Mp3Metadata;
import com.epam.elastic.service.IndexingService;
import com.epam.elastic.service.SongBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.Message;

@Slf4j
@Component
public class Listener {

    @Autowired
    private SongBuilder songBuilder;
    @Autowired
    private MessageConverter messageConverter;
    @Autowired
    private IndexingService indexingService;


    @JmsListener(destination = "meta")
    public void onMessage(Message message) {
        try{
            Mp3Metadata mp3Metadata = (Mp3Metadata) messageConverter.fromMessage(message);
            Song song = songBuilder.create(mp3Metadata);
            indexingService.save(song);
        } catch(Exception e) {
            log.error("Received Exception : "+ e);
        }
    }
}