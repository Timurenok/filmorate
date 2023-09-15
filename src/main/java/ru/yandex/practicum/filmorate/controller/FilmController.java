package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        filmService.createFilm(film);
        log.info("Добавление фильма {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        filmService.updateFilm(film);
        log.info("Обновление фильма {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получение фильмов");
        return filmService.getFilms();
    }

    @GetMapping(value = "/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Получение фильма с идентификатором {}", id);
        return filmService.getFilm(id);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Добавление лайка фильму с идентификатором {} от пользователя с идентификатором {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        log.info("Удаление лайка у фильма с идентификатором {} пользователем с идентификатором {}", id, userId);
        filmService.deleteLike(id, userId);
    }

    @GetMapping(value = "/popular")
    public List<Film> getTopFilms(@RequestParam(defaultValue = "10") int count) {
        log.info("Получение топ {} фильмов", count);
        return filmService.getTopFilms(count);
    }
}
