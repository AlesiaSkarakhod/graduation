package ru.javawebinar.graduation.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class RestaurantTo extends BaseTo {

    private String name;

    private int votes;

    public RestaurantTo(Integer id, String name, int votes) {
        super(id);
        this.name = name;
        this.votes = votes;
    }
}
