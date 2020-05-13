package ru.javawebinar.graduation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "menu_unique_restaurant_id_date_idx")})
public class Menu extends AbstractBaseEntity {

    @Column(name = "name", nullable = false)
    @Size(min = 2, max = 100)
    @NotBlank
    private String name;

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

    public Menu(Integer id) {
        super(id);
    }

    public Menu(String name) {
        super(null);
        this.name = name;
    }

    public Menu(String name, @NotNull LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public Menu(Integer id, String name, @NotNull LocalDate date) {
        super(id);
        this.name = name;
        this.date = date;
    }

}