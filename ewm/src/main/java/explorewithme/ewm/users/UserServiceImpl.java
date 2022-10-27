package explorewithme.ewm.users;

import explorewithme.ewm.exception.NotFoundException;
import explorewithme.ewm.exception.ValidationException;
import explorewithme.ewm.users.dto.NewUserRequest;
import explorewithme.ewm.users.dto.UserDto;
import explorewithme.ewm.users.model.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Transactional
    @Override
    public UserDto create(NewUserRequest newUserRequest) {
        if (newUserRequest.getEmail() == null) {
            log.debug("email cannot be empty");
            throw new ValidationException("email cannot be empty");
        }
        User user = UserMapper.fromNewUserRequest(newUserRequest);
        log.debug("save user to repo");
        User userToReturn = userRepository.save(user);
        return UserMapper.toUserDto(userToReturn);
    }

    @Override
    public void checkId(long id) {
        if (userRepository.findById(id).isEmpty()){
            log.debug("user with id "+ id + " not found");
            throw new NotFoundException("user with id "+ id + " not found");
        }
    }

    @Transactional
    @Override
    public UserDto update(UserDto userDto, long id) {
        checkId(id);
        User existUser = userRepository.getReferenceById(id);
        if (userDto.getEmail()!=null){
            log.debug("setting email to new email");
            existUser.setEmail(userDto.getEmail());
        }
        if (userDto.getName()!=null){
            log.debug("setting name to new name");
               existUser.setName(userDto.getName());
        }
        log.debug("saving updated user");
        User userToReturn = userRepository.save(existUser);
        return UserMapper.toUserDto(userToReturn);
    }
    @Transactional
    @Override
    public void delete(long id) {
        checkId(id);
        log.debug("Asking to delete user");
        User user = userRepository.getReferenceById(id);
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getUsers() {
        log.debug("Retriving users from repo");
        return   userRepository.findAll()
                .stream().map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(long id) {
        try {
            User userToReturn = userRepository.getReferenceById(id);
            return UserMapper.toUserDto(userToReturn);
        } catch (EntityNotFoundException e) {
            log.debug("user with id "+ id + " not found");
            throw new NotFoundException("user with id "+ id + " not found");
        }
    }


}
