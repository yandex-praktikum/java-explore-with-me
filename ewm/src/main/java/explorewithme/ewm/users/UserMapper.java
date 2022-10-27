package explorewithme.ewm.users;


import explorewithme.ewm.users.dto.NewUserRequest;
import explorewithme.ewm.users.dto.UserDto;
import explorewithme.ewm.users.dto.UserShortDto;
import explorewithme.ewm.users.model.User;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getName(),
                user.getEmail()
        );
        userDto.setId(user.getId());
        return userDto;
    }

    public static User fromNewUserRequest(NewUserRequest newUserRequest) {
        return new User(
                0L,
                newUserRequest.getName(),
                newUserRequest.getEmail()
        );
    }

    public static UserShortDto fromUserDtoToShort(UserDto userDto) {
        return new UserShortDto(
                userDto.getId(),
                userDto.getName()
        );
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(
                user.getId(),
                user.getName()
        );
    }
}
