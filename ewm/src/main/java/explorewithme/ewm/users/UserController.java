package explorewithme.ewm.users;


import explorewithme.ewm.users.dto.NewUserRequest;
import explorewithme.ewm.users.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Метод, который добавляет нового пользователя
    @PostMapping
    public UserDto create(@Valid @RequestBody NewUserRequest newUserRequest) throws RuntimeException {
        return userService.create(newUserRequest);
    }

    // Метод удаляющий пользователя
    @DeleteMapping("/{userId}")
    public void delete(@PathVariable long userId) throws RuntimeException {
        userService.delete(userId);
    }

    // Метод по получению всех пользователей
    @GetMapping
    public List<UserDto> getUsers() throws RuntimeException {
        return userService.getUsers();
    }

}
