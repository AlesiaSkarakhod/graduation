package ru.javawebinar.graduation.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.SafeHtml;
import ru.javawebinar.graduation.util.HasIdAndEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserTo extends BaseTo implements HasIdAndEmail, Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 100)
    @SafeHtml
    private String name;

    @Email
    @NotBlank
    @Size(max = 100)
    @SafeHtml // https://stackoverflow.com/questions/17480809
    private String email;

    @NotBlank
    @Size(min = 5, max = 32)
    private String password;

    public UserTo(Integer id, String name, String email, String password) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
