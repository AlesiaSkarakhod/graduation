package ru.javawebinar.graduation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menu_unique_restaurant_id_date_idx")})
public class Menu extends AbstractNamedEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    private List<Dish> dishes;

    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    public Menu() {
        date = LocalDateTime.now().toLocalDate();
    }

    public Menu(String name) {
        super(null, name);
    }

    public Menu(String name, @NotNull LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public Menu(Integer id, String name, @NotNull LocalDate date) {
        super(id, name);
        this.date = date;
    }

    public Menu(Integer id, String name, @NotNull Restaurant restaurant, List<Dish> dishes, @NotNull LocalDate date) {
        super(id, name);
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.date = date;
    }
}