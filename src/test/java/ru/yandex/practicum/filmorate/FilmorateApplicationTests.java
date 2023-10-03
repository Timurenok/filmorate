package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.db.FilmDbStorage;
import ru.yandex.practicum.filmorate.db.UserDbStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;

	@BeforeEach
	public void beforeEachCreateFilm() {
		Film film = new Film();
		film.setId(1);
		film.setName("barbie");
		film.setDescription("af");
		film.setDuration(100);
		film.setReleaseDate(LocalDate.parse("2023-08-09"));
		filmStorage.createFilm(film);
	}

	@Test
	public void testCreateFilm() {
		Film film = new Film();
		film.setId(2);
		film.setName("barbie");
		film.setDescription("af");
		film.setDuration(100);
		film.setReleaseDate(LocalDate.parse("2023-08-09"));
		filmStorage.createFilm(film);
		Assertions.assertEquals(film, filmStorage.getFilm(2));
	}

	@Test
	public void testUpdateFilm() {
		Film film = new Film();
		film.setId(1);
		film.setName("b");
		film.setDescription("a");
		film.setDuration(10);
		film.setReleaseDate(LocalDate.parse("2023-08-10"));
		filmStorage.updateFilm(film);
		Assertions.assertEquals(film, filmStorage.getFilm(1));
	}

	@Test
	public void testGetAllFilms() {
		Assertions.assertNotNull(filmStorage.getFilms());
	}

	@Test
	public void testFindFilmById() {
		Film film = new Film();
		film.setId(2);
		film.setName("barbie");
		film.setDescription("af");
		film.setDuration(100);
		film.setReleaseDate(LocalDate.parse("2023-08-09"));
		filmStorage.createFilm(film);
		Assertions.assertEquals(film, filmStorage.getFilm(2));
	}

	@BeforeEach
	public void beforeEachCreateUser() {
		User user = new User();
		user.setId(0);
		user.setEmail("email");
		user.setLogin("login");
		user.setName("name");
		user.setBirthday(LocalDate.parse("2007-09-27"));
		userStorage.createUser(user);
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setId(2);
		user.setEmail("email");
		user.setLogin("login");
		user.setName("name");
		user.setBirthday(LocalDate.parse("2007-09-27"));
		userStorage.createUser(user);
		Assertions.assertEquals(user, userStorage.getUser(2));
	}

	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setId(1);
		user.setEmail("e");
		user.setLogin("l");
		user.setName("n");
		user.setBirthday(LocalDate.parse("2007-09-30"));
		userStorage.updateUser(user);
		Assertions.assertEquals(user, userStorage.getUser(1));
	}

	@Test
	public void testGetAllUsers() {
		Assertions.assertNotNull(userStorage.getUsers());
	}

	@Test
	public void testFindUserById() {
		User user = new User();
		user.setId(2);
		user.setEmail("email");
		user.setLogin("login");
		user.setName("name");
		user.setBirthday(LocalDate.parse("2007-09-27"));
		userStorage.createUser(user);
		Assertions.assertEquals(user, userStorage.getUser(2));
	}
}
