package ru.javawebinar.graduation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "dish")
public class Dish extends AbstractBaseEntity {

    @NotBlank
    @Size(min = 2, max = 100)
    @Column(name = "name", nullable = false)
    protected String name;

    @Column(name = "price")
    @Range(min = 0, max = 1000000)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "menu_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Menu menu;

    public Dish() {
    }

    public Dish(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Dish(Integer id, String name, BigDecimal price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    public Dish(Integer id, String name, BigDecimal price, @NotNull Menu menu) {
        super(id);
        this.name = name;
        this.price = price;
        this.menu = menu;
    }
}