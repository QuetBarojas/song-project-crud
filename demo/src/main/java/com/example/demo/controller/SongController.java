package com.example.demo.controller;


import com.example.demo.entity.Song;
import com.example.demo.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping(value = "/api")
public class SongController {
    @Autowired
    private ISongService songService;

    @GetMapping(value = "/songs")
    public List<Song> getAll() {
        return songService.findAll();
    }

    @GetMapping(value = "/songs/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        Song song = null;

        Map<String, Object> response = new HashMap<>();
        try {
            song = songService.findById(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (song == null) {
            response.put("mensaje", "LA cancion con el id ".concat(id.toString())
                    .concat(" no se encuentra registrado en la base de datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Song>(song, HttpStatus.OK);
    }

    @PostMapping(value = "/songs")
    public ResponseEntity<?> create(@RequestBody Song song, BindingResult result) {
        Song songNew = null;

        Map<String, Object> response = new HashMap<>();
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            songNew = songService.save(song);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la inserción");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "La cancion ha sido creada con exito");
        response.put("song", songNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/songs/{id}")
    public ResponseEntity<?> update(@RequestBody Song song, BindingResult result,
                                    @PathVariable Integer id) {
        Song songCurrent = songService.findById(id);
        Song songUpdate = songCurrent;

        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }

        if (songCurrent == null) {
            response.put("mensaje", "Error: no se puede editar cancion con el id ".concat(id.toString())
                    .concat(" no existen datos"));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        try {
            songCurrent.setName(song.getName());
            songUpdate = songService.save(songCurrent);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar cancion");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cancion actualizada correctamente");
        response.put("song", songUpdate);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/songs/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Map<String, Object> response = new HashMap<>();
        try {
            songService.delete(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar cancion");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje", "Cancion eliminada con exito");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

}
