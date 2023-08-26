package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController() {
        filmService = new FilmService();
    }

    @PostMapping
    public Film postFilm(@RequestBody Film film) {
        filmService.filmStorage.postFilm(film);
        log.info("Добавление фильма {}", film);
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        filmService.filmStorage.putFilm(film);
        log.info("Обновление фильма {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Получение фильмов");
        return filmService.filmStorage.getFilms();
    }

    @GetMapping(value = "/{id}")
    public Film getFilm(@PathVariable int id) {
        log.info("Получение фильма с идентификатором {}", id);
        return filmService.filmStorage.getFilm(id);
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
