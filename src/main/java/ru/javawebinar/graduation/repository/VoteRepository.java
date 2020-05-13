package ru.javawebinar.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.graduation.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id=:id AND v.user.id = :userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Override
    <S extends Vote> S save(S entity);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.date = :date AND v.user.id = :userId")
    Vote getByDateAndUser(@Param("date") LocalDate date, @Param("userId") int userId);

    List<Vote> getAllByUserId(int userId);

    @Query("SELECT COUNT(v) FROM Vote v WHERE v.restaurant.id=:restaurantId and v.date = current_date")
    int countVoteByDate(@Param("restaurantId") int restaurantId);

}
