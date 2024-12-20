package ru.yandex.practicum.filmorate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FilmMapperUtil.class})
public interface FilmMapper {

    @Mapping(target = "genres", qualifiedByName = {"FilmMapperUtil", "getGenre"}, source = "film")
    @Mapping(target = "mpa", qualifiedByName = {"FilmMapperUtil", "getMpa"}, source = "film")
    FilmDto toFilmDto(Film film);

    @Mapping(target = "genres", qualifiedByName = {"FilmMapperUtil", "getGenresId"}, source = "filmDto")
    @Mapping(target = "mpaId", qualifiedByName = {"FilmMapperUtil", "getMpaId"}, source = "filmDto")
    Film toFilm(FilmDto filmDto);
}
