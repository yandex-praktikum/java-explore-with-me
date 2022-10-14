package ru.practikum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.explorewithme.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(List<Long> id);
}
