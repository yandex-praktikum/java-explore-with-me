package ru.explorewithme.admin.users;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.explorewithme.admin.dto.NewUserRequest;
import ru.explorewithme.admin.dto.UserDto;
import ru.explorewithme.admin.model.User;
import ru.explorewithme.exception.IdException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto addUser(NewUserRequest newUserRequest) {
        User user = UserMapper.toUser(newUserRequest);

        UserDto userDto = UserMapper.toUserDto(
                userRepository.save(user));
        log.info("Added user: {}", userDto);
        return userDto;
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new IdException("no user with such id"));

        userRepository.deleteById(userId);
    }

    public List<UserDto> getUsers(Set<Long> ids, Integer from, Integer size) {
        if (ids == null || ids.size() == 0) {
            Pageable pageable = PageRequest.of(from / size, size);
            return userRepository.findAll(pageable).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
        } else return userRepository.getUsersAtIds(ids).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }
}
