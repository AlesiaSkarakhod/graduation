package ru.javawebinar.graduation.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduation.model.Menu;
import ru.javawebinar.graduation.model.Role;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.service.MenuService;
import ru.javawebinar.graduation.service.UserService;
import ru.javawebinar.graduation.util.View;
import ru.javawebinar.graduation.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MenuController {
    static final String REST_URL = "/rest/restaurant/{restaurantId}/menu";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserService userService;

    @GetMapping("/{menuId}")
    public Menu get(@PathVariable int menuId) {
        Menu menu = menuService.get(menuId);
        log.info("get menu with id {}", menuId);
        return menu;
    }

    @GetMapping("currentDate")
    public Menu getOneMenuByCurrentDate(@PathVariable int restaurantId) {
        log.info("get updated menu");
        return menuService.getOneMenuByCurrentDate(restaurantId);
    }

    @GetMapping
    public List<Menu> getAll(@PathVariable int restaurantId) {
        log.info("getAll menus");
        return menuService.getAll(restaurantId);
    }

    @PutMapping(value = "/{menuId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Menu menu,
                       @PathVariable int restaurantId) {
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("update menu");
            menuService.create(menu, restaurantId);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> createWithLocation(@Validated(View.Web.class) @RequestBody Menu menu,
                                                   @PathVariable int restaurantId) {
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("create menu");
            Menu created = menuService.create(menu, restaurantId);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return null;
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int menuId) {
        log.info("delete menu {}", menuId);
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            menuService.delete(menuId);
        }
    }
}