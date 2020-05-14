package ru.javawebinar.graduation.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.javawebinar.graduation.model.AbstractBaseEntity;
import ru.javawebinar.graduation.model.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class VoteTo extends BaseTo {

    private LocalDate date;
    private LocalTime time;
    private String restaurant;
    private User user;

    public VoteTo(Integer id, LocalDate date, LocalTime time, String restaurant, User user) {
        super(id);
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
        this.user = user;
    }
}
