package server;

import org.springframework.data.jpa.repository.JpaRepository;
import server.model.Hit;

public interface HitRepository extends JpaRepository<Hit, Long> {
}