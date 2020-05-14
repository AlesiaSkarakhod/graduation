package ru.javawebinar.graduation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Getter
@Setter
@ToString
@Entity
@Table(name = "dish")
public class Dish extends AbstractNamedEntity {

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
        super(id, name);
        this.price = price;
    }

    public Dish(Integer id, String name, BigDecimal price, @NotNull Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }
}