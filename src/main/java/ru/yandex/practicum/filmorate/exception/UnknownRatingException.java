package ru.yandex.practicum.filmorate.exception;

public class UnknownRatingException extends RuntimeException {
    public UnknownRatingException(final String message) {
        super(message);
    }
}
