package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.util.DateFormatter;

import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
public class Film {
    private int id;
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
    private Set<Integer> likes = new HashSet<>();
    private int mpaId;
    private Collection<Integer> genres = new ArrayList<>();

    public Film(String name, String description, String releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.parse(releaseDate, DateFormatter.DATE_FORMATTER);
        this.duration = duration;
    }

    public Film(int id, String name, String description, LocalDate releaseDate, int duration, int mpaId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpaId = mpaId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        if (mpaId != 0) {
            values.put("mpa_id", mpaId);
        }
        return values;
    }
}
