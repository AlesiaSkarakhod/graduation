package ru.javawebinar.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Dish d WHERE d.id=:id")
    int delete(@Param("id") int id);

    List<Dish> getAllByMenuId(int menuId);

    @Query("SELECT d FROM Dish d WHERE d.id = :id AND d.menu.id = :menuId")
    Dish getByMenuId(@Param("id") int id, @Param("menuId") int menuId);
}
