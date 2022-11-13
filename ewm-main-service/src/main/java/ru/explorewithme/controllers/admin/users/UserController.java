package ru.explorewithme.controllers.admin.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.explorewithme.user.dto.NewUserRequest;
import ru.explorewithme.user.dto.UserDto;
import ru.explorewithme.user.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/users")
@Slf4j
@Validated
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto addUser(@RequestBody @Valid NewUserRequest newUserRequest) {
        log.info("Creating new user: {}", newUserRequest);

        return userService.addUser(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@Positive(message = "id can't be < or = 0") @PathVariable Long userId) {
        log.info("Deleting user with id = {}", userId);
        userService.deleteUser(userId);
    }

    @GetMapping
    List<UserDto> getUsers(@RequestParam(required = false) HashSet<Long> ids,
                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("Getting users with ids = {}, from={}, size={}", ids, from, size);
        return userService.getUsers(ids, from, size);
    }
}
