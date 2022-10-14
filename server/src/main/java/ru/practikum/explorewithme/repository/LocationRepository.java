package ru.practikum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practikum.explorewithme.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
