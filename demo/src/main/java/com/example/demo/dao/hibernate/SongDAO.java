package com.example.demo.dao.hibernate;

import com.example.demo.entity.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongDAO extends CrudRepository<Song,Integer> {
}
