package ru.javawebinar.graduation.secvice;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.javawebinar.graduation.model.Role;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.service.UserService;
import ru.javawebinar.graduation.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.graduation.TestUtil.validateRootCause;
import static ru.javawebinar.graduation.UserTestData.*;

@RunWith(value = BlockJUnit4ClassRunner.class)
@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void create() {
        User newUser = getNew();
        User created = userService.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(userService.get(newId), newUser);
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class,
                () -> userService.create(new User(null, "Duplicate", "user1@gmail.com", "new_password", Role.USER)));
    }

    @Test
    void createWithException() {
        validateRootCause(() -> userService.create(new User(null, "  ", "user1@gmail.com", "password1", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User1", "  ", "password1", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User1", "user1@ gmail.com", "password1", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User1", "user1gmailcom", "  ", Role.USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User1", "user1@gmail.com", "password1", true, null, Set.of())), ConstraintViolationException.class);
    }

    @Test
    void update() throws Exception {
        User updated = getUpdated();
        userService.update(updated);
        USER_MATCHER.assertMatch(userService.get(USER_ID), updated);
    }

    @Test
    void delete() throws Exception {
        userService.delete(USER_ID);
        assertThrows(NotFoundException.class,
                () -> userService.delete(USER_ID));
    }

    @Test
    void deletedNotFound() throws Throwable {
        assertThrows(NotFoundException.class,
                () -> userService.delete(1));
    }

    @Test
    void get() throws Exception {
        USER_MATCHER.assertMatch(userService.get(ADMIN_ID), ADMIN);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class,
                () -> userService.get(1));
    }

    @Test
    void getByEmail() throws Exception {
        USER_MATCHER.assertMatch(userService.getByEmail("admin@gmail.com"), ADMIN);
    }

    @Test
    void getAll() throws Exception {
        USER_MATCHER.assertMatch(userService.getAll(), ADMIN, USER);
    }

}