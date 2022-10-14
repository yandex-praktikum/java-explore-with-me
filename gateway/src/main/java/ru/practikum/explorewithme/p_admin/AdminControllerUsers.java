package ru.practikum.explorewithme.p_admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practikum.explorewithme.dto.in.NewUserRequest;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping(path = "/admin/users")
public class AdminControllerUsers {

    private final AdminClientUsers clientUsers;

    @GetMapping
    public ResponseEntity<Object> getUsers(@RequestParam(required = false) Optional<Long[]> ids,
                                           @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                           @Positive @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET users with usersId={}, from={}, size={}", ids, from, size);
        return clientUsers.getUsers(ids, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid NewUserRequest userRequest) {
        log.info("POST create user " + userRequest);
        return clientUsers.createUser(userRequest);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable long userId) {
        log.info("DELETE user id={}", userId);
        return clientUsers.deleteUser(userId);
    }
}
