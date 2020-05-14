package ru.javawebinar.graduation;


import ru.javawebinar.graduation.model.Role;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.graduation.model.AbstractBaseEntity.START_SEQ;


public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator(User.class, "registered", "roles", "votes", "password");

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "USER", "user1@gmail.com", "password1", Role.USER);
    public static final User ADMIN = new User(ADMIN_ID, "ADMIN", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);

    public static final List<User> USERS = List.of(ADMIN, USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}
