package ru.practicum.explore_with_me.ewm_main_service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.NewUserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.UserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {

    private static final String HEADER_USER_ROLE = "X-Explore-With-Me-User-Role";
    private final UserService userService;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody NewUserDto userDto,
                              @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Создание пользователя, name={}, email={}", userDto.getName(), userDto.getEmail());
        return userService.createUser(userDto, role);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId,
                           @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Получение пользователя, id={}", userId);
        return userService.getUser(userId, role);
    }

    @GetMapping
    public List<UserDto> findUser(@RequestParam(required = false) Long[] ids,
                                  @RequestParam(defaultValue = "0") int from,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Поиск пользователей, ids={}, from={}, size={}", ids, from, size);
        return userService.findUser(ids, from, size, role);
    }

    @DeleteMapping("/{userId}")
    public void removeUser(@PathVariable Long userId,
                           @RequestHeader(HEADER_USER_ROLE) String role) {
        log.info("Удаление пользователя, id={}", userId);
        userService.removeUser(userId, role);
    }
}
