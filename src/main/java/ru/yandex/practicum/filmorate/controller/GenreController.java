package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@RestController
@RequestMapping("/genres")
@Slf4j
@RequiredArgsConstructor
public class GenreController {
    private final GenreStorage genreStorage;

    @GetMapping
    public List<Genre> getGenres() {
        log.info("Получение жанров");
        return genreStorage.getGenres();
    }

    @GetMapping(value = "/{id}")
    public Genre getGenre(@PathVariable int id) {
        return genreStorage.getGenre(id);
    }
}
