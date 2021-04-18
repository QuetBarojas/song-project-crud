package com.example.demo.service;

import com.example.demo.entity.Song;

import java.util.List;

public interface ISongService {
    public List<Song> findAll();

    public Song findById(Integer id);

    public Song save(Song song);

    public void delete(Integer id);
}
