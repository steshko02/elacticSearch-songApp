package com.epam.elastic.document;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Deprecated
public class SongList {

    private List<Song> songList;

    public SongList(){
        songList=new ArrayList<>();
    }
    public void addSong(Song song){
            songList.add(song);
    }

}
