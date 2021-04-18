package com.example.demo.service;

import com.example.demo.dao.hibernate.SongDAO;
import com.example.demo.entity.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly=true)
@Service
public class SongServiceImpl implements ISongService{
    @Autowired
    SongDAO songDAO;

    @Override
    public List<Song> findAll() {
        return (List<Song>) songDAO.findAll();
    }

    @Override
    public Song findById(Integer id) {
        return songDAO.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Song save(Song song) {
        return songDAO.save(song);
    }

    @Override
    public void delete(Integer id) {

    }
}
