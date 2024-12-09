package ru.yandex.practicum.filmorate.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class FilmDto {
    private int id;
    @NotBlank(message = "Film name should not be empty")
    private String name;
    @Size(max = 200, message = "Maximum description length - 200 symbols")
    private String description;
    private LocalDate releaseDate;
    @Positive(message = "Film duration should be positive")
    private int duration;
    private Set<Integer> likes = new HashSet<>();
    private Mpa mpa;
    private Collection<Genre> genres = new ArrayList<>();
}
