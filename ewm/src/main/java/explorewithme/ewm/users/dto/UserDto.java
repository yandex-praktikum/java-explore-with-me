package explorewithme.ewm.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String name;

    private String email;


    public UserDto(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
