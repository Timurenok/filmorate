package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.List;

@RestController
@RequestMapping("/mpa")
@Slf4j
@RequiredArgsConstructor
public class RatingController {
    private final RatingStorage ratingStorage;

    @GetMapping
    public List<Rating> getRatings() {
        log.info("Получение рейтингов");
        return ratingStorage.getRatings();
    }

    @GetMapping(value = "/{id}")
    public Rating getRating(@PathVariable int id) {
        return ratingStorage.getRating(id);
    }
}