package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.util.DateFormatter;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;

    public Film(String name, String description, String releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate, DateFormatter.DATE_FORMATTER);
        this.duration = duration;
    }
}
