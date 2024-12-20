package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.util.DateFormatter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
public class User {
    private int id;
    @NotBlank(message = "Email should not be empty")
    private String email;
    @NotBlank(message = "Login should not be empty")
    private String login;
    private String name;
    @PastOrPresent(message = "Birthday should not be in the future")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public User(String email, String login, String name, String birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = LocalDate.parse(birthday, DateFormatter.DATE_FORMATTER);
    }

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("email", email);
        values.put("login", login);
        values.put("name", name);
        values.put("birthday", birthday);
        return values;
    }
}
