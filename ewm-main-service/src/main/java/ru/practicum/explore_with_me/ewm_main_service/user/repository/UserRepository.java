package ru.practicum.explore_with_me.ewm_main_service.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.ewm_main_service.user.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.id in ?1")
    List<User> findUsersInArray(Long[] ids);

    @Query("select u from User u where u.id in ?1")
    Page<User> findUsersInArray(Long[] ids, Pageable pageable);

    Boolean existsUserByName(String name);
}
