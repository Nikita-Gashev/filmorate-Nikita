package ru.yandex.practicum.filmorate.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.storage.FilmMpaStorage;
import ru.yandex.practicum.filmorate.storage.FilmWithGenreStorage;

import java.util.stream.Collectors;

@Service
public class MappingUtils {

    private final FilmWithGenreStorage filmWithGenreStorage;
    private final FilmMpaStorage filmMpaStorage;

    @Autowired
    public MappingUtils(FilmWithGenreStorage filmWithGenreStorage, FilmMpaStorage filmMpaStorage) {
        this.filmWithGenreStorage = filmWithGenreStorage;
        this.filmMpaStorage = filmMpaStorage;
    }

    public FilmDto mapToFilmDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setDuration(film.getDuration());
        filmDto.setLikes(film.getLikes());
        filmDto.setMpa(filmMpaStorage.getById(film.getMpaId()));
        filmDto.setGenres(filmWithGenreStorage.getGenresByFilmId(film.getId()));
        return filmDto;
    }

    public Film mapToFilm(FilmDto filmDto) {
        Film film = new Film();
        film.setId(filmDto.getId());
        film.setName(filmDto.getName());
        film.setDescription(filmDto.getDescription());
        film.setReleaseDate(filmDto.getReleaseDate());
        film.setDuration(filmDto.getDuration());
        film.setLikes(filmDto.getLikes());
        if (filmDto.getMpa() != null) {
            film.setMpaId(filmDto.getMpa().getId());
        }
        if (!filmDto.getGenres().isEmpty()) {
            film.setGenres(filmDto.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList()));
        }
        return film;
    }
}
