package ru.yandex.practicum.filmorate.exception;

public class UnknownUserException extends RuntimeException {
    public UnknownUserException(final String message) {
        super(message);
    }
}
