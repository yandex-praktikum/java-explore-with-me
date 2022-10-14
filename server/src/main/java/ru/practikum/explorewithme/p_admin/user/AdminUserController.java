package ru.practikum.explorewithme.p_admin.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.UserDto;
import ru.practikum.explorewithme.model.User;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/users")
public class AdminUserController {

    private final AdminUserService service;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam Long[] ids,
                                  @RequestParam Integer from,
                                  @RequestParam Integer size) {
        log.info("SERVER GET users with usersId={}, from={}, size={}", ids, from, size);
        return service.getUsers(ids, from, size);
    }

    @PostMapping
    public UserDto createUser(@RequestBody User user) {
        log.info("SERVER POST create user");
        return service.createUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        log.info("SERVER DELETE user id={}", userId);
        service.deleteUser(userId);
    }
}
