package ru.explorewithme.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.explorewithme.IdService;
import ru.explorewithme.user.dto.NewUserRequest;
import ru.explorewithme.user.dto.UserDto;
import ru.explorewithme.user.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;
    private IdService idService;

    public UserService(UserRepository userRepository, IdService idService) {

        this.userRepository = userRepository;
        this.idService = idService;
    }

    public UserDto addUser(NewUserRequest newUserRequest) {
        User user = UserMapper.toUser(newUserRequest);

        UserDto userDto = UserMapper.toUserDto(
                userRepository.save(user));
        log.info("Added user: {}", userDto);
        return userDto;
    }

    public void deleteUser(Long userId) {
        idService.getUserById(userId);

        userRepository.deleteById(userId);
        log.info("Deleted user with id={}", userId);
    }

    public List<UserDto> getUsers(Set<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.size() == 0) {
            Pageable pageable = PageRequest.of(from / size, size);
            List<UserDto> users = userRepository.findAll(pageable)
                    .stream().map(UserMapper::toUserDto).collect(Collectors.toList());
            log.info("Getted users: {}", users);
            return users;
        } else {
            List<UserDto> users = userRepository.getUsersAtIds(ids)
                    .stream().map(UserMapper::toUserDto).collect(Collectors.toList());
            log.info("Getted users: {}", users);
            return users;
        }
    }
}
