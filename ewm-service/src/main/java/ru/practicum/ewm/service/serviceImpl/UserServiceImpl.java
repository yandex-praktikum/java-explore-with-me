package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.dto.newDto.NewUserDto;
import ru.practicum.ewm.repository.UserRepository;
import ru.practicum.ewm.service.UserService;
import ru.practicum.ewm.service.mapper.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository repository;

    @Override
    public List<UserDto> getAll(List<Long> ids, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return repository.findByIdIn(ids, pageable).stream().map(UserMapper::toUserDto).collect(Collectors.toList());
    }

    @Override
    public UserDto create(NewUserDto dto) {
        return UserMapper.toUserDto(repository.save(UserMapper.toUserFromNewDto(dto)));
    }

    @Override
    public void delete(long userId) {
        repository.deleteById(userId);
    }
}
