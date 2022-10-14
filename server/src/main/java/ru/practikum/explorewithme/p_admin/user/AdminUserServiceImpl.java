package ru.practikum.explorewithme.p_admin.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practikum.explorewithme.dto.UserDto;
import ru.practikum.explorewithme.model.User;
import ru.practikum.explorewithme.pageable.OffsetLimitPageable;
import ru.practikum.explorewithme.repository.UserRepository;

import java.util.Arrays;
import java.util.List;

import static ru.practikum.explorewithme.mapper.UserMapper.toDto;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminUserServiceImpl implements  AdminUserService {

    private final UserRepository repository;

    @Override
    public List<UserDto> getUsers(Long[] ids, Integer from, Integer size) {
        if (ids.length > 0) {
            return toDto(repository.findByIdIn(Arrays.asList(ids)));
        } else {
            Pageable pageable = OffsetLimitPageable.of(from, size);
            return toDto(repository.findAll(pageable).toList());
        }
    }

    @Override
    @Transactional
    public UserDto createUser(User user) {
        return toDto(repository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        repository.deleteById(userId);
    }
}
