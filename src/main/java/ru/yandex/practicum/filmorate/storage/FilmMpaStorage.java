package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface FilmMpaStorage {

    List<Mpa> getAll();

    Mpa getById(int mpaId);

    List<Integer> getAllMpaId();
}
