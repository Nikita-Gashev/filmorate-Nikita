package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
public class GenresController {

    private final GenreService genreService;

    @Autowired
    public GenresController(GenreStorage genreStorage, GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAll() {
        log.info("Request received GET /genres");
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public Genre getById(@PathVariable Integer id) {
        log.info("Request received GET /genres/{}", id);
        return genreService.getById(id);
    }
}
