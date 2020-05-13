package ru.javawebinar.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Menu;

import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Override
    <S extends Menu> S save(S entity);

    List<Menu> findAllByRestaurantId(int restaurantId);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId and m.date = current_date")
    Menu getOneByCurrentDate(@Param("restaurantId") int restaurantId);
}
