package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.model.Menu;
import ru.javawebinar.graduation.repository.MenuRepository;
import ru.javawebinar.graduation.repository.RestaurantRepository;

import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Transactional
    public Menu create(Menu menu, int restaurantId) {
        Assert.notNull(menu, "menu must not be null");
        menu.setRestaurant(restaurantRepository.getOne(restaurantId));
        Menu save = menuRepository.save(menu);
        return checkNotFoundWithId(save, save.id());
    }

    public void delete(int id) {
        checkNotFoundWithId(menuRepository.delete(id) != 0, id);
    }

    public Menu get(int id) {
        return checkNotFoundWithId(menuRepository.findById(id).orElse(null), id);
    }

    public List<Menu> getAll(int restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public Menu getOneMenuByCurrentDate(int restaurantId) {
        return menuRepository.getOneByCurrentDate(restaurantId);
    }
}
