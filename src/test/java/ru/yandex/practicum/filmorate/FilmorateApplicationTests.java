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
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
	private final UserDbStorage userStorage;
	private final FilmDbStorage filmStorage;

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setId(1);
		user.setEmail("email@mail.com");
		user.setLogin("login");
		user.setName("name");
		user.setBirthday(LocalDate.parse("2007-09-27"));
		userStorage.createUser(user);
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
		user.setEmail("email@gmail.com");
		user.setLogin("login");
		user.setName("name");
		user.setBirthday(LocalDate.parse("2007-09-27"));
		userStorage.createUser(user);
		Assertions.assertEquals(user, userStorage.getUser(2));
	}
}
