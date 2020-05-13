package ru.javawebinar.graduation.web.dish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduation.model.Dish;
import ru.javawebinar.graduation.model.Role;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.service.DishService;
import ru.javawebinar.graduation.service.UserService;
import ru.javawebinar.graduation.util.View;
import ru.javawebinar.graduation.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishController {

    static final String REST_URL = "/rest/restaurant/{restaurantId}/menu/{menuId}/dish";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DishService dishService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<Dish> getAll(@PathVariable int menuId) {
        log.info("getAll");
        return dishService.getAll(menuId);
    }

    @GetMapping("/{dishId}")
    public Dish get(@PathVariable int menuId, @PathVariable int dishId) {
        log.info("get dish {} for menu {}", dishId, menuId);
        return dishService.getByMenuId(dishId, menuId);
    }

    @PostMapping(value = "/{restaurantId}/menus/{menuId}/dishes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Validated @RequestBody Dish dish,
                                                   //@PathVariable int restaurantId,
                                                   @PathVariable int menuId) {
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("create");
            Dish created = dishService.create(dish, menuId);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return null;
    }

    @PutMapping(value = "/{dishId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated(View.Web.class) @RequestBody Dish dish,
                       @PathVariable int menuId) {
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("update dish ");
            dishService.create(dish, menuId);
        }
    }

    @DeleteMapping("/{dishId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int dishId) {
        log.info("delete dish {}", dishId);
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            dishService.delete(dishId);
        }
    }
}
