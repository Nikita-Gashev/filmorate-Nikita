package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.storage.*;
import ru.yandex.practicum.filmorate.util.DateFormatter;

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
    private final FilmMapper filmMapper;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserService userService, FilmWithGenreStorage filmWithGenreStorage,
                       FilmLikesStorage filmLikesStorage, GenreStorage genreStorage, FilmMpaStorage filmMpaStorage,
                       FilmMapper filmMapper) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.filmWithGenreStorage = filmWithGenreStorage;
        this.filmLikesStorage = filmLikesStorage;
        this.genreStorage = genreStorage;
        this.filmMpaStorage = filmMpaStorage;
        this.filmMapper = filmMapper;
    }

    public FilmDto add(FilmDto filmDto) {
        Film film = filmMapper.toFilm(filmDto);
        validation(film);
        filmStorage.add(film);
        if (!(film.getGenres().isEmpty())) {
            film.setGenres(film.getGenres().stream()
                    .distinct()
                    .collect(Collectors.toList()));
            filmWithGenreStorage.setFilmWithGenre(film);
        }
        log.info("Film '{}' added", film.getName());
        filmDto = filmMapper.toFilmDto(film);
        return filmDto;
    }

    private void validation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28", DateFormatter.DATE_FORMATTER))) {
            throw new ValidationException("Release date should be after 1895-12-28");
        }
        if (!(genreStorage.getAllGenreId().containsAll(film.getGenres()))) {
            throw new IncorrectParameterException("Genre ID is not correct");
        }
        if (!(filmMpaStorage.getAllMpaId().contains(film.getMpaId()))) {
            throw new IncorrectParameterException("Mpa ID is not correct");
        }
    }

    @Transactional
    public FilmDto update(FilmDto filmDto) {
        Film film = filmMapper.toFilm(filmDto);
        getById(film.getId());
        filmStorage.update(film);
        filmWithGenreStorage.updateFilmWithGenre(film);
        filmLikesStorage.getFilmWithLikes(film);
        filmDto = filmMapper.toFilmDto(film);
        log.info("Film '{}' update", film.getName());
        return filmDto;
    }

    public FilmDto getById(int id) {
        try {
            Film film = filmStorage.getById(id).get();
            filmWithGenreStorage.getFilmWithGenresId(film);
            filmLikesStorage.getFilmWithLikes(film);
            log.info("Get film  with id: {}", id);
            return filmMapper.toFilmDto(film);
        } catch (EmptyResultDataAccessException e) {
            throw new FilmNotFoundException("Film not found");
        }
    }

    public List<FilmDto> getAll() {
        log.info("Get list of films");
        return filmStorage.getAll().stream()
                .map(filmWithGenreStorage::getFilmWithGenresId)
                .map(filmLikesStorage::getFilmWithLikes)
                .map(filmMapper::toFilmDto)
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
