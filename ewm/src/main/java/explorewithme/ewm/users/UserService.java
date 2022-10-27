package explorewithme.ewm.users;

import explorewithme.ewm.users.dto.NewUserRequest;
import explorewithme.ewm.users.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    @Transactional
    UserDto create(NewUserRequest newUserRequest);

    void checkId(long id);

    @Transactional
    UserDto update(UserDto userDto, long id);

    @Transactional
    void delete(long id);

    List<UserDto> getUsers();

    UserDto getUserById(long id);
}
