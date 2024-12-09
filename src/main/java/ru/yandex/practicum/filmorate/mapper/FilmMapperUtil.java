package ru.yandex.practicum.filmorate.mapper;

import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.storage.FilmMpaStorage;
import ru.yandex.practicum.filmorate.storage.FilmWithGenreStorage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named("FilmMapperUtil")
@Component
public class FilmMapperUtil {

    private final FilmWithGenreStorage filmWithGenreStorage;
    private final FilmMpaStorage filmMpaStorage;

    @Autowired
    public FilmMapperUtil(FilmWithGenreStorage filmWithGenreStorage, FilmMpaStorage filmMpaStorage) {
        this.filmWithGenreStorage = filmWithGenreStorage;
        this.filmMpaStorage = filmMpaStorage;
    }

    @Named("getGenre")
    public List<Genre> getGenre(Film film) {
        return filmWithGenreStorage.getGenresByFilmId(film.getId());
    }

    @Named("getMpa")
    public Mpa getMpa(Film film) {
        return filmMpaStorage.getById(film.getMpaId());
    }

    @Named("getGenresId")
    public List<Integer> getGenresId(FilmDto filmDto) {
        if (!filmDto.getGenres().isEmpty()) {
            return filmDto.getGenres().stream()
                    .map(Genre::getId).
                    collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Named("getMpaId")
    public Integer getMpaId(FilmDto filmDto) {
        if (filmDto.getMpa() != null) {
            return filmDto.getMpa().getId();
        }
        return null;
    }
}
