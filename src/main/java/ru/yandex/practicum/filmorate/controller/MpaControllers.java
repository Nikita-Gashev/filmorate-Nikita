package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;
import ru.yandex.practicum.filmorate.storage.FilmMpaStorage;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
public class MpaControllers {

    private final MpaService mpaService;

    @Autowired
    public MpaControllers(FilmMpaStorage filmMpaStorage, MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public List<Mpa> getAll() {
        log.info("Request received GET /mpa");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public Mpa getById(@PathVariable Integer id) {
        log.info("Request received GET /mpa/{}", String.valueOf(id));
        return mpaService.getById(id);
    }
}
