package ru.yandex.practicum.filmorate.exception;

public class UnknownFilmException extends RuntimeException {
    public UnknownFilmException(final String message) {
        super(message);
    }
}
