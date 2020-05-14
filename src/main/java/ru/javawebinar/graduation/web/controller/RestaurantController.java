package ru.javawebinar.graduation.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.graduation.model.Restaurant;
import ru.javawebinar.graduation.model.Role;
import ru.javawebinar.graduation.model.User;
import ru.javawebinar.graduation.service.RestaurantService;
import ru.javawebinar.graduation.service.UserService;
import ru.javawebinar.graduation.to.RestaurantTo;
import ru.javawebinar.graduation.web.SecurityUtil;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    static final String REST_URL = "/rest/restaurant";

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable int restaurantId) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        log.info("get {}", restaurantId);
        return restaurant;
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId) {
        log.info("delete {}", restaurantId);
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            restaurantService.delete(restaurantId);
        }
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("update restaurant");
            restaurantService.create(restaurant);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated @RequestBody Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        User user = userService.get(userId);
        if (user.getRoles().contains(Role.ADMIN)) {
            log.info("create");
            Restaurant created = restaurantService.create(restaurant);

            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL + "/{id}")
                    .buildAndExpand(created.getId()).toUri();

            return ResponseEntity.created(uriOfNewResource).body(created);
        }
        return null;
    }

}
