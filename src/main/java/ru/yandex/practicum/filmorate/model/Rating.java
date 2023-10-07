package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class Rating {
    private int id;
    private String name;

    public Rating(int id) {
        this.id = id;
    }

    public Rating(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
