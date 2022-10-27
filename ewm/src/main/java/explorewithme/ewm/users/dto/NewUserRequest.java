package explorewithme.ewm.users.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class NewUserRequest {

    @Email
    String email;
    String name;
}
