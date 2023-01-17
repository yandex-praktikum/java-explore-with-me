package ru.practicum.explore_with_me.ewm_main_service.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.AccessForbiddenException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.ConflictArgumentsException;
import ru.practicum.explore_with_me.ewm_main_service.exceptions.NotFoundException;
import ru.practicum.explore_with_me.ewm_main_service.handler.Error;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.NewUserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.dto.UserDto;
import ru.practicum.explore_with_me.ewm_main_service.user.mapper.UserMapper;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;
import ru.practicum.explore_with_me.ewm_main_service.user.repository.UserRepository;
import ru.practicum.explore_with_me.ewm_main_service.utils.RoleEnum;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.explore_with_me.ewm_main_service.utils.ParametersValid.pageValidated;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User createUser(User user) {
        if (userRepository.existsUserByName(user.getName())) {
            throw new ConflictArgumentsException(this.getClass().getName(),
                    "User already exist", "Пользователь с заданными именем уже существует.");
        }
        User createdUser = userRepository.save(user);
        log.debug("{} has been added.", createdUser);
        return createdUser;
    }

    @Override
    public UserDto createUser(NewUserDto userDto, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            User createdUser = createUser(userMapper.toUser(userDto));
            return userMapper.toDto(createdUser);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Override
    public User getUser(Long userId) {
        return getUserOrThrow(userId);
    }

    @Override
    public UserDto getUser(Long userId, String role) {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            return userMapper.toDto(getUser(userId));
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Override
    public List<User> findUser(Long[] ids, Integer from, Integer size) {
        Optional<PageRequest> pageRequest = pageValidated(from, size);
        if (ids == null) {
            return pageRequest
                    .map(request -> userRepository.findAll(request).toList())
                    .orElseGet(userRepository::findAll);
        } else {
            if (pageRequest.isPresent()) {
                return userRepository.findUsersInArray(ids, pageRequest.get()).toList();
            } else {
                return userRepository.findUsersInArray(ids);
            }
        }
    }

    @Override
    public List<UserDto> findUser(Long[] ids, Integer from, Integer size, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            return findUser(ids, from, size)
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toList());
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    @Transactional
    @Override
    public void removeUser(Long userId, String role) throws AccessForbiddenException {
        if (RoleEnum.of(role) == RoleEnum.ADMINISTRATOR) {
            User user = getUserOrThrow(userId);
            userRepository.delete(user);
        } else {
            throw new AccessForbiddenException(this, role);
        }
    }

    private User getUserOrThrow(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(List.of(new Error(this.getClass().getName(),
                        String.format("User %d not found.", userId))),
                        "Пользователь с заданным индексом отсутствует.",
                        String.format("User %d not found.", userId)));
    }
}
