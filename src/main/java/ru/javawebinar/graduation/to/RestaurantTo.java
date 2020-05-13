package ru.javawebinar.graduation.to;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javawebinar.graduation.model.AbstractBaseEntity;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class RestaurantTo extends AbstractBaseEntity implements Serializable {

    private String name;

    private int votes;

    public RestaurantTo() {
    }

    public RestaurantTo(Integer id, String name, int votes) {
        super(id);
        this.name = name;
        this.votes = votes;
    }
}
