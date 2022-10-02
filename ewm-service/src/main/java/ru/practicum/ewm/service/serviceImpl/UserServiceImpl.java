package ru.practicum.ewm.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.UserDto;
import ru.practicum.ewm.dto.newDto.NewUserDto;
import ru.practicum.ewm.service.UserService;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public List<UserDto> getAll(List<Long> ids, int from, int size) {
        return null;
    }

    @Override
    public UserDto create(NewUserDto dto) {
        return null;
    }

    @Override
    public void delete(long userId) {

    }
}
