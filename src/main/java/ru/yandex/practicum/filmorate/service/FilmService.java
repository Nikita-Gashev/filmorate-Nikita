package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.storage.*;
import ru.yandex.practicum.filmorate.util.DateFormatter;
import ru.yandex.practicum.filmorate.util.MappingUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserService userService;
    private final FilmWithGenreStorage filmWithGenreStorage;
    private final FilmLikesStorage filmLikesStorage;
    private final GenreStorage genreStorage;
    private final FilmMpaStorage filmMpaStorage;
    private final MappingUtils mappingUtils;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService, FilmWithGenreStorage filmWithGenreStorage, FilmLikesStorage filmLikesStorage, GenreStorage genreStorage, FilmMpaStorage filmMpaStorage, MappingUtils mappingUtils) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.filmWithGenreStorage = filmWithGenreStorage;
        this.filmLikesStorage = filmLikesStorage;
        this.genreStorage = genreStorage;
        this.filmMpaStorage = filmMpaStorage;
        this.mappingUtils = mappingUtils;
    }

    public FilmDto add(FilmDto filmDto) {
        Film film = mappingUtils.mapToFilm(filmDto);
        if (film.getName().isBlank()) {
            throw new ValidationException("Film name should not be empty");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Maximum description length - 200 symbols");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28", DateFormatter.DATE_FORMATTER))) {
            throw new ValidationException("Release date should be after 1895-12-28");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("Film duration should be positive");
        }
        if (!(genreStorage.getAllGenreId().containsAll(film.getGenres()))) {
            throw new IncorrectParameterException("Genre ID is not correct");
        }
        if (!(filmMpaStorage.getAllMpaId().contains(film.getMpaId()))) {
            throw new IncorrectParameterException("Mpa ID is not correct");
        }
        filmStorage.add(film);
        if (!(film.getGenres().isEmpty())) {
            film.setGenres(film.getGenres().stream()
                    .distinct()
                    .collect(Collectors.toList()));
            filmWithGenreStorage.setFilmWithGenre(film);
        }
        log.info("Film '{}' added", film.getName());
        filmDto = mappingUtils.mapToFilmDto(film);
        return filmDto;
    }

    public FilmDto update(FilmDto filmDto) {
        Film film = mappingUtils.mapToFilm(filmDto);
        getById(film.getId());
        filmStorage.update(film);
        filmWithGenreStorage.updateFilmWithGenre(film);
        filmLikesStorage.getFilmWithLikes(film);
        filmDto = mappingUtils.mapToFilmDto(film);
        log.info("Film '{}' update", film.getName());
        return filmDto;
    }

    public FilmDto getById(int id) {
        try {
            Film film = filmStorage.getById(id).get();
            filmWithGenreStorage.getFilmWithGenresId(film);
            filmLikesStorage.getFilmWithLikes(film);
            return mappingUtils.mapToFilmDto(film);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException("Film not found");
        }
    }

    public List<FilmDto> getAll() {
        return filmStorage.getAll().stream()
                .map(filmWithGenreStorage::getFilmWithGenresId)
                .map(filmLikesStorage::getFilmWithLikes)
                .map(mappingUtils::mapToFilmDto)
                .collect(Collectors.toList());
    }

    public FilmDto userSetLike(int userId, int filmId) {
        filmLikesStorage.userSetLikes(userId, filmId);
        log.info("User '{}' set like '{}'", userService.getById(userId).getLogin(), getById(filmId).getName());
        return getById(filmId);
    }

    public FilmDto userRemoveLike(int userId, int filmId) {
        filmLikesStorage.userRemoveLikes(userId, filmId);
        log.info("User '{}' remove like '{}'", userService.getById(userId).getLogin(), getById(filmId).getName());
        return getById(filmId);
    }

    public List<FilmDto> getPopularFilms(int count) {
        if (count < 0) {
            throw new IncorrectParameterException("Count should be positive");
        }
        return getAll().stream()
                .sorted((f0, f1) -> Integer.compare(f1.getLikes().size(), f0.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
