package ru.javawebinar.graduation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.graduation.model.Dish;
import ru.javawebinar.graduation.repository.DishRepository;
import ru.javawebinar.graduation.repository.MenuRepository;

import java.util.List;

import static ru.javawebinar.graduation.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public Dish create(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setMenu(menuRepository.getOne(menuId));
        Dish save = dishRepository.save(dish);
        return checkNotFoundWithId(save, save.id());
    }

    @Transactional
    public void update(Dish dish, int menuId) {
        Assert.notNull(dish, "dish must not be null");
        dish.setMenu(menuRepository.getOne(menuId));
        checkNotFoundWithId(dishRepository.save(dish), dish.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    public Dish getByMenuId(int id, int menuId) {
        return checkNotFoundWithId(dishRepository.getByMenuId(id, menuId), id);
    }

    public List<Dish> getAll(int menuId) {
        return dishRepository.getAllByMenuId(menuId);
    }
}
