package ru.yandex.practicum.filmorate.validation;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}

