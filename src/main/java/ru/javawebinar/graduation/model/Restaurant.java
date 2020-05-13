package ru.javawebinar.graduation.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_unique_name_idx")})
public class Restaurant extends AbstractBaseEntity {

    @Column(name = "name")
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Menu> menus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Vote> votes;


    public Restaurant() {
        name = "";
    }

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }

    public Restaurant(String name) {
        super(null);
        this.name = name;
    }

    public Restaurant(String title, List<Menu> menus) {
        this(null, title, menus);
    }

    public Restaurant(Integer id, String name, List<Menu> menus) {
        super(id);
        this.name = name;
        this.menus = menus;
    }
}
