package ru.yandex.practicum.filmorate.exception;

public class UnknownGenreException extends RuntimeException {
    public UnknownGenreException(final String message) {
        super(message);
    }
}