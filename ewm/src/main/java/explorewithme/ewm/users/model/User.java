package explorewithme.ewm.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "users", schema = "public")
@ToString
@Data
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String name;
    @Email(message = "check email formatting")
    @NotNull(message = "Should not be null")
    @NotBlank(message = "Should not be blank")
    @Column(unique = true,nullable = false)
    String email;

    public User() {

    }
}
